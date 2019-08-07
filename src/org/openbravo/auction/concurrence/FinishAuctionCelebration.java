package org.openbravo.auction.concurrence;

import java.util.Date;

import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;

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

    new OpenbravoAuctionServiceImpl().finishAuctionCelebration(auctionId);

    System.out.println("LA SUBASTA " + auctionId + " HA FINALIZADO.");
  }
}
