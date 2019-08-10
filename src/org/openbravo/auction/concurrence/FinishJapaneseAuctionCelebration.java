package org.openbravo.auction.concurrence;

import java.util.Date;

import org.openbravo.auction.service.impl.JapaneseAuctionServiceImpl;

public class FinishJapaneseAuctionCelebration implements Runnable {
  private String auctionId;
  private Date deadLine;

  public FinishJapaneseAuctionCelebration(String auctionId, Date deadLine) {
    this.auctionId = auctionId;
    this.deadLine = deadLine;
  }

  @Override
  public void run() {
    while (new Date().compareTo(deadLine) != 1) {
      // System.out.println("CELEBRÁNDOSE... " + auctionId);
    }

    new JapaneseAuctionServiceImpl().finishAuctionCelebration(auctionId);

    System.out.println("LA SUBASTA " + auctionId + " HA FINALIZADO.");
  }
}
