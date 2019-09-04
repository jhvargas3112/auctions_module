package org.openbravo.auction.service;

import java.math.BigDecimal;
import java.util.Date;

import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.model.JapaneseAuctionBuyer;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public interface JapaneseAuctionService {
  public Long getPeriodOfTimeToIncrementPrice(Date celebrationDate, Date deadLine,
      Integer numberOfRounds);

  public BigDecimal getAmountToIncrementPrice(BigDecimal startingPrice, BigDecimal maximumSalePrice,
      Integer numberOfRounds);

  public BigDecimal incrementJapaneseAuctionItemPrice(String dutchAuctionId,
      BigDecimal amountToReduceOn);

  public void finishAuctionCelebration(String japaneseAuctionId);

  public Boolean finishAuctionCelebration(String japaneseAuctionId, String japaneseAuctionBuyerId);

  public Boolean CheckIfThereIsAWinner(JapaneseAuction japaneseAuction);

  public JapaneseAuctionBuyer determineJapaneseAuctionWinner(JapaneseAuction japaneseAuction);
}
