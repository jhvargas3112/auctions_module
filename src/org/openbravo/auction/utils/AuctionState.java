package org.openbravo.auction.utils;

public class AuctionState {
  private AuctionStateEnum auctionStateEnum;
  private String auctionStateName;

  public AuctionState(AuctionStateEnum auctionState) {
    this.auctionStateEnum = auctionState;

    switch (this.auctionStateEnum) {
      case PUBLISHED:
        auctionStateName = "Publicada";
        break;
      case IT_IS_CELEBRATING:
        auctionStateName = "Celebrándose";
        break;
      case FINISHED_WITH_WINNER:
        auctionStateName = "Finalizada con ganador";
        break;
      case FINISHED_WITHOUT_WINNER:
        auctionStateName = "Finalizada sin ganador";
        break;
      case CANCELLED:
        auctionStateName = "Cancelada";
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  public AuctionStateEnum getAuctionStateEnum() {
    return auctionStateEnum;
  }

  public String getAuctionStateName() {
    return auctionStateName;
  }
}
