package org.openbravo.auction.concurrence;

import java.util.Date;

import org.openbravo.auction.service.impl.DutchAuctionServiceImpl;

public class FinishDutchAuctionCelebration implements Runnable {
  private String auctionId;
  private Date deadLine;

  public FinishDutchAuctionCelebration(String auctionId, Date deadLine) {
    this.auctionId = auctionId;
    this.deadLine = deadLine;
  }

  @Override
  public void run() {
    while (new Date().compareTo(deadLine) != 1) {
    }

    new DutchAuctionServiceImpl().finishAuctionCelebration(auctionId);

    System.out.println("LA SUBASTA " + auctionId + " HA FINALIZADO.");
  }
}
