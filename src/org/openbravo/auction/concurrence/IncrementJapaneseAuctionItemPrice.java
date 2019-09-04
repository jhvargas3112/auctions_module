package org.openbravo.auction.concurrence;

import java.math.BigDecimal;

import org.openbravo.auction.service.impl.JapaneseAuctionServiceImpl;

public class IncrementJapaneseAuctionItemPrice implements Runnable {
  private String auctionId;

  private BigDecimal startingPrice;
  private BigDecimal maximumSalePrice;
  private Integer numberOfRounds;

  private Long periodOfTimeToIncrementPrice;
  private BigDecimal amountToDecrease;
  private Integer roundsConsumed = 1;

  public IncrementJapaneseAuctionItemPrice(String auctionId, BigDecimal startingPrice,
      BigDecimal maximumSalePrice, Integer numberOfRounds, Long periodOfTimeToIncrementPrice,
      BigDecimal amountToDecrease) {
    this.auctionId = auctionId;
    this.startingPrice = startingPrice;
    this.maximumSalePrice = maximumSalePrice;
    this.numberOfRounds = numberOfRounds;
    this.periodOfTimeToIncrementPrice = periodOfTimeToIncrementPrice;
    this.amountToDecrease = amountToDecrease;
  }

  @Override
  public void run() {
    while (roundsConsumed <= numberOfRounds && maximumSalePrice.compareTo(startingPrice) == 1) {
      try {
        Thread.sleep(periodOfTimeToIncrementPrice);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      startingPrice = new JapaneseAuctionServiceImpl().incrementJapaneseAuctionItemPrice(auctionId,
          amountToDecrease);

      roundsConsumed++;
    }
  }
}
