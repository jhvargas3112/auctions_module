package org.openbravo.auction.concurrence;

import java.util.Date;

import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.auction.utils.AuctionTypeEnum;

public class StartAuctionCelebration implements Runnable {
  private String auctionId;
  private Date celebrationDate;
  private Date deadLine;
  private AuctionTypeEnum auctionType;

  public StartAuctionCelebration(String auctionId, Date celebrationDate, Date deadLine,
      AuctionTypeEnum auctionType) {
    this.auctionId = auctionId;
    this.celebrationDate = celebrationDate;
    this.deadLine = deadLine;
    this.auctionType = auctionType;
  }

  @Override
  public void run() {
    while (new Date().compareTo(celebrationDate) != 1) {
    }

    OpenbravoAuctionServiceImpl openbravoAuctionServiceImpl = new OpenbravoAuctionServiceImpl();

    if (new OpenbravoAuctionServiceImpl().countAuctionBuyers(auctionId) >= 2) {
      openbravoAuctionServiceImpl.startAuctionCelebration(auctionId);

      switch (auctionType) {
        case ENGLISH:
          new Thread(new FinishEnglishAuctionCelebration(auctionId, deadLine)).start();
          break;
        case DUTCH:
          new Thread(new FinishDutchAuctionCelebration(auctionId, deadLine)).start();
          break;
        case JAPANESE:
          new Thread(new FinishJapaneseAuctionCelebration(auctionId, deadLine)).start();
          break;
      }
    } else {
      openbravoAuctionServiceImpl.cancelAuctionCelebration(auctionId);
    }
  }
}
