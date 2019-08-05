package org.openbravo.auction.concurrence;

import java.util.Date;

public class FinishAuctionCelebration implements Runnable {
  private String auctionId;
  private Date deadLine;

  public FinishAuctionCelebration(String auctionId, Date deadLine) {
    this.auctionId = auctionId;
    this.deadLine = deadLine;
  }

  @Override
  public void run() {
    while (new Date().compareTo(deadLine) != 1) {
      // System.out.println("CELEBRÁNDOSE... " + auctionId);
    }

    System.out.println("LA SUBASTA " + auctionId + " HA FINALIZADO.");

    // new OpenbravoAuctionServiceImpl().finishAuctionCelebration(auctionId);
  }
}
