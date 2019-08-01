package org.openbravo.auction.concurrence;

import org.openbravo.auction.service.impl.DutchAuctionServiceImpl;

public class ReduceAuctionItemPrice implements Runnable {
  private Double startingPrice;
  private Double minimumSalePrice;
  private Integer numberOfRounds;

  private Long period;
  private Double amountToDecrease;
  private Integer roundsConsumed = 1;

  public ReduceAuctionItemPrice(Double startingPrice, Double minimumSalePrice,
      Integer numberOfRounds, Long period, Double amountToDecrease) {
    super();
    this.startingPrice = startingPrice;
    this.minimumSalePrice = minimumSalePrice;
    this.numberOfRounds = numberOfRounds;
    this.period = period;
    this.amountToDecrease = amountToDecrease;
  }

  @Override
  public void run() {
    while (roundsConsumed <= numberOfRounds && startingPrice > minimumSalePrice) {
      try {
        Thread.sleep(period);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println("SE REDUCE EL PRECIO EN " + amountToDecrease);
      startingPrice = new DutchAuctionServiceImpl().reduceDutchAuctionItemPrice(amountToDecrease);

      System.out.println("PRECIO ACTUAL: " + startingPrice);

      roundsConsumed++;
    }
  }
}
