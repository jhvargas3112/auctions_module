package org.openbravo.auction.rest.server;

public enum OpenbravoAuctionRestServer {
  INSTANCE;

  private OpenbravoAuctionRestApplication openbravoAuctionRestApplication = null;
  private Boolean isStarted = false;

  public void start() {
    if (openbravoAuctionRestApplication == null) {
      openbravoAuctionRestApplication = new OpenbravoAuctionRestApplication();
      openbravoAuctionRestApplication.startOpenbravoAuctionRestServer();
      isStarted = true;
    }
  }

  public Boolean isStarted() {
    return isStarted;
  }
}
