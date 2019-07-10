package org.openbravo.auction.service;

import java.util.Date;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public interface JapaneseAuctionService {
  public Long getPeriodOfTimeToDecreasePrice(Date celebrationDate, Date deadLine,
      Integer numberOfRounds);

  public Double getAmountToDecreasePrice(Double startingPrice, Double minimumSalePrice,
      Integer numberOfRounds);

  public Double reduceJapaneseAuctionItemPrice(Double amountToReduceOn);

  public void determineJapaneseAuctionWinner();
}
