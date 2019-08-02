package org.openbravo.auction.concurrence;

import java.math.BigDecimal;

import org.openbravo.auction.service.impl.DutchAuctionServiceImpl;

public class ReduceAuctionItemPrice implements Runnable {
  private Integer auctionId;

  private BigDecimal startingPrice;
  private BigDecimal minimumSalePrice;
  private Integer numberOfRounds;

  private Long period;
  private BigDecimal amountToDecrease;
  private Integer roundsConsumed = 1;

  public ReduceAuctionItemPrice(Integer auctionId, BigDecimal startingPrice,
      BigDecimal minimumSalePrice, Integer numberOfRounds, Long period,
      BigDecimal amountToDecrease) {
    this.auctionId = auctionId;
    this.startingPrice = startingPrice;
    this.minimumSalePrice = minimumSalePrice;
    this.numberOfRounds = numberOfRounds;
    this.period = period;
    this.amountToDecrease = amountToDecrease;
  }

  @Override
  public void run() {
    while (roundsConsumed <= numberOfRounds && startingPrice.compareTo(minimumSalePrice) == 1) {
      try {
        Thread.sleep(period);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println("SE REDUCE EL PRECIO EN " + amountToDecrease);
      startingPrice = new DutchAuctionServiceImpl().reduceDutchAuctionItemPrice(auctionId,
          amountToDecrease);

      System.out.println("PRECIO ACTUAL: " + startingPrice.doubleValue());

      roundsConsumed++;
    }
  }
}
