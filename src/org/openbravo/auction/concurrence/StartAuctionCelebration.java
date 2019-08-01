package org.openbravo.auction.concurrence;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
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
    while (!DateUtils.isSameInstant(celebrationDate, new Date())) {
      System.out.println("ESPERANDO... " + auctionId);
    }

    System.out.println("HOY " + new Date() + " EMPIEZA A CELEBRARSE LA SUBASTA");

    new OpenbravoAuctionServiceImpl().startAuctionCelebration(auctionId);
  }
}
