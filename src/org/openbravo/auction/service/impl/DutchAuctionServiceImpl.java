package org.openbravo.auction.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.DutchAuctionBuyer;
import org.openbravo.auction.service.DutchAuctionService;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.XMLUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class DutchAuctionServiceImpl implements DutchAuctionService {
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
  public BigDecimal reduceDutchAuctionItemPrice(String dutchAuctionId, BigDecimal amountToReduce) {
    ClientResource clientResource = new ClientResource(
        "http://localhost:8111/openbravo/auction/reduce_item_price");
    clientResource.addQueryParameter("auction_id", dutchAuctionId.toString());

    Representation responseData = clientResource.post(amountToReduce);

    BigDecimal priceAfterTheDecrement = null;

    try {
      priceAfterTheDecrement = new BigDecimal(Double.parseDouble(responseData.getText()));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    return priceAfterTheDecrement;
  }

  @Override
  public void finishAuctionCelebration(String dutchAuctionId) {
    new OpenbravoAuctionServiceImpl().changeAuctionState(dutchAuctionId,
        AuctionStateEnum.FINISHED_WITHOUT_WINNER);
  }

  @Override
  public void finishAuctionCelebration(String dutchAuctionId, String dutchAuctionBuyerId) {
    OpenbravoAuctionServiceImpl openbravoAuctionServiceImpl = new OpenbravoAuctionServiceImpl();

    DutchAuction dutchAuction = (DutchAuction) new OpenbravoAuctionServiceImpl()
        .getAuction(dutchAuctionId);

    openbravoAuctionServiceImpl.changeAuctionState(dutchAuctionId,
        AuctionStateEnum.FINISHED_WITH_WINNER);

    DutchAuctionBuyer winner = determineDutchAuctionWinner((DutchAuction) dutchAuction,
        dutchAuctionBuyerId);

    openbravoAuctionServiceImpl.notifyAuctionWinner(dutchAuctionId, winner.getEmail());

    ClientResource clientResource = new ClientResource(
        "http://localhost:8111/openbravo/auction/set_winner");
    clientResource.addQueryParameter("auction_id", dutchAuctionId);

    clientResource.put(winner.getEmail());

    new XMLUtils().saveAuctionWinner(dutchAuctionId, dutchAuction.getDeadLine().toString(),
        winner.getEmail(), dutchAuction.getItem().getName(), dutchAuction.getCurrentPrice());
  }

  @SuppressWarnings("unchecked")
  @Override
  public DutchAuctionBuyer determineDutchAuctionWinner(DutchAuction dutchAuction,
      String dutchAuctionBuyerId) {
    Iterator<DutchAuctionBuyer> it = ((TreeSet<DutchAuctionBuyer>) dutchAuction.getAuctionBuyers())
        .iterator();

    DutchAuctionBuyer winner = null;

    while (it.hasNext()) {
      winner = it.next();

      if (StringUtils.equals(dutchAuctionBuyerId, winner.getId())) {
        break;
      }
    }

    return winner;
  }
}
