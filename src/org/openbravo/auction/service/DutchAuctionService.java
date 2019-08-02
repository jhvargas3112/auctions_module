package org.openbravo.auction.service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public interface DutchAuctionService {
  public Long getPeriodOfTimeToDecreasePrice(Date celebrationDate, Date deadLine,
      Integer numberOfRounds);

  public BigDecimal getAmountToDecreasePrice(BigDecimal startingPrice, BigDecimal minimumSalePrice,
      Integer numberOfRounds);

  public BigDecimal reduceDutchAuctionItemPrice(Integer auctionId, BigDecimal amountToReduce);

  public void determineDutchAuctionWinner();
}
