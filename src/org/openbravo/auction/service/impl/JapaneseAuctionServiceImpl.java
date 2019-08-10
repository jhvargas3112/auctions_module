package org.openbravo.auction.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
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
  public Long getPeriodOfTimeToDecreasePrice(Date celebrationDate, Date deadLine,
      Integer numberOfRounds) {
    return (Math.abs(deadLine.getTime() - celebrationDate.getTime())) / numberOfRounds;
  }

  @Override
  public BigDecimal getAmountToDecreasePrice(BigDecimal startingPrice, BigDecimal minimumSalePrice,
      Integer numberOfRounds) {
    return (startingPrice.subtract(minimumSalePrice))
        .divide(new BigDecimal(Double.parseDouble(String.valueOf(numberOfRounds))));
  }

  @Override
  public BigDecimal reduceJapaneseAuctionItemPrice(BigDecimal amountToReduceOn) {
    Representation responseData = new ClientResource(
        "http://localhost:8111/openbravo/auction/reduce_item_price").post(amountToReduceOn);

    BigDecimal priceAfterTheDecrement = null;

    try {
      priceAfterTheDecrement = new BigDecimal(Double.parseDouble(responseData.getText()));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    return priceAfterTheDecrement;
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
