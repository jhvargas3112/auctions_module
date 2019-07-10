package org.openbravo.auction.service.impl;

import java.io.IOException;
import java.util.Date;

import org.openbravo.auction.service.JapaneseAuctionService;
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
  public Double getAmountToDecreasePrice(Double startingPrice, Double minimumSalePrice,
      Integer numberOfRounds) {
    return (startingPrice - minimumSalePrice) / Double.parseDouble(String.valueOf(numberOfRounds));
  }

  @Override
  public Double reduceJapaneseAuctionItemPrice(Double amountToReduceOn) {
    Representation responseData = new ClientResource(
        "http://localhost:8111/openbravo/auction/reduce_item_price").post(amountToReduceOn);

    Double priceAfterTheDecrement = null;

    try {
      priceAfterTheDecrement = Double.parseDouble(responseData.getText());
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    return priceAfterTheDecrement;
  }

  @Override
  public void determineJapaneseAuctionWinner() {

  }

}
