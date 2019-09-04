package org.openbravo.auction.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.model.JapaneseAuctionBuyer;
import org.openbravo.auction.service.JapaneseAuctionService;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.XMLUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class JapaneseAuctionServiceImpl implements JapaneseAuctionService {

  @Override
  public Long getPeriodOfTimeToIncrementPrice(Date celebrationDate, Date deadLine,
      Integer numberOfRounds) {
    return (Math.abs(deadLine.getTime() - celebrationDate.getTime())) / numberOfRounds;
  }

  @Override
  public BigDecimal getAmountToIncrementPrice(BigDecimal startingPrice, BigDecimal maximumSalePrice,
      Integer numberOfRounds) {
    return (maximumSalePrice.subtract(startingPrice)).divide(
        new BigDecimal(Double.parseDouble(String.valueOf(numberOfRounds))), RoundingMode.HALF_DOWN);
  }

  @Override
  public BigDecimal incrementJapaneseAuctionItemPrice(String dutchAuctionId,
      BigDecimal amountToIncrementOn) {
    ClientResource clientResource = new ClientResource(
        "http://localhost:8111/openbravo/auction/increment_item_price");
    clientResource.addQueryParameter("auction_id", dutchAuctionId.toString());

    Representation responseData = clientResource.post(amountToIncrementOn);

    BigDecimal priceAfterTheIncrement = null;

    try {
      priceAfterTheIncrement = new BigDecimal(Double.parseDouble(responseData.getText()));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    return priceAfterTheIncrement;
  }

  @Override
  public void finishAuctionCelebration(String japaneseAuctionId) {
    new OpenbravoAuctionServiceImpl().changeAuctionState(japaneseAuctionId,
        AuctionStateEnum.FINISHED_WITHOUT_WINNER);
  }

  @Override
  public Boolean finishAuctionCelebration(String japaneseAuctionId, String japaneseAuctionBuyerId) {
    OpenbravoAuctionServiceImpl openbravoAuctionServiceImpl = new OpenbravoAuctionServiceImpl();

    JapaneseAuction japaneseAuction = (JapaneseAuction) openbravoAuctionServiceImpl
        .getAuction(japaneseAuctionId);

    JapaneseAuctionBuyer winner = determineJapaneseAuctionWinner(japaneseAuction);

    boolean resp = false;

    if (winner != null) {
      openbravoAuctionServiceImpl.changeAuctionState(japaneseAuctionId,
          AuctionStateEnum.FINISHED_WITH_WINNER);

      openbravoAuctionServiceImpl.notifyAuctionWinner(japaneseAuctionId, winner.getEmail());

      ClientResource clientResource = new ClientResource(
          "http://localhost:8111/openbravo/auction/set_winner");
      clientResource.addQueryParameter("auction_id", japaneseAuctionId);

      clientResource.put(winner.getEmail());

      new XMLUtils().saveAuctionWinner(japaneseAuctionId, japaneseAuction.getDeadLine().toString(),
          winner.getEmail(), japaneseAuction.getItem().getName(),
          japaneseAuction.getCurrentPrice());

      resp = true;
    }

    return resp;
  }

  @Override
  public Boolean CheckIfThereIsAWinner(JapaneseAuction japaneseAuction) {
    return japaneseAuction.getAuctionState()
        .getAuctionStateEnum() == AuctionStateEnum.IT_IS_CELEBRATING
        && japaneseAuction.getAuctionBuyers().size() == 1;
  }

  @Override
  public JapaneseAuctionBuyer determineJapaneseAuctionWinner(JapaneseAuction japaneseAuction) {
    if (CheckIfThereIsAWinner(japaneseAuction)) {
      return (JapaneseAuctionBuyer) japaneseAuction.getAuctionBuyers().first();
    } else {
      return null;
    }
  }
}
