package org.openbravo.auction.service;

import java.math.BigDecimal;
import java.util.Date;

import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.DutchAuctionBuyer;

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

  public BigDecimal reduceDutchAuctionItemPrice(String dutchAuctionId, BigDecimal amountToReduce);

  public void finishAuctionCelebration(String dutchAuctionId);

  public void finishAuctionCelebration(String dutchAuctionId, String dutchAuctionBuyerId);

  public DutchAuctionBuyer determineDutchAuctionWinner(DutchAuction dutchAuction,
      String dutchAuctionBuyerId);
}
