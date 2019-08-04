package org.openbravo.auction.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.openbravo.auction.service.DutchAuctionService;
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
  public BigDecimal reduceDutchAuctionItemPrice(String auctionId, BigDecimal amountToReduce) {
    ClientResource clientResource = new ClientResource(
        "http://localhost:8111/openbravo/auction/reduce_item_price");
    clientResource.addQueryParameter("auction_id", auctionId.toString());

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
  public void determineDutchAuctionWinner() {

  }
}
