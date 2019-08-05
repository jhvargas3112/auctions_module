package org.openbravo.auction.concurrence;

import java.util.Date;

import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;

public class StartAuctionCelebration implements Runnable {
  private String auctionId;
  private Date celebrationDate;
  private Date deadLine;

  public StartAuctionCelebration(String auctionId, Date celebrationDate, Date deadLine) {
    this.auctionId = auctionId;
    this.celebrationDate = celebrationDate;
    this.deadLine = deadLine;
  }

  @Override
  public void run() {
    while (new Date().compareTo(celebrationDate) != 1) {
    }

    OpenbravoAuctionServiceImpl openbravoAuctionServiceImpl = new OpenbravoAuctionServiceImpl();

    if (new OpenbravoAuctionServiceImpl().countAuctionBuyers(auctionId) >= 2) {
      openbravoAuctionServiceImpl.startAuctionCelebration(auctionId);
      new Thread(new FinishAuctionCelebration(auctionId, deadLine)).start();
    } else {
      openbravoAuctionServiceImpl.cancelAuctionCelebration(auctionId);
    }
  }
}
