package org.openbravo.auction.concurrence;

import java.util.Date;

import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;

public class StartAuctionCelebration implements Runnable {
  private Integer auctionId;
  private Date celebrationDate;

  public StartAuctionCelebration(Integer auctionId, Date celebrationDate) {
    this.auctionId = auctionId;
    this.celebrationDate = celebrationDate;
  }

  @Override
  public void run() {
    while (new Date().compareTo(celebrationDate) != 1) {
    }

    OpenbravoAuctionServiceImpl openbravoAuctionServiceImpl = new OpenbravoAuctionServiceImpl();

    if (new OpenbravoAuctionServiceImpl().countAuctionBuyers(auctionId) >= 2) {
      openbravoAuctionServiceImpl.startAuctionCelebration(auctionId);
    } else {
      openbravoAuctionServiceImpl.cancelAuctionCelebration(auctionId);
    }
  }
}
