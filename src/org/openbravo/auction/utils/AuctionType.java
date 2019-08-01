package org.openbravo.auction.utils;

public class AuctionType {
  private AuctionTypeEnum auctionTypeEnum;
  private String auctionTypeName;

  public AuctionType(AuctionTypeEnum auctionType) {
    this.auctionTypeEnum = auctionType;

    switch (this.auctionTypeEnum) {
      case ENGLISH:
        auctionTypeName = "Inglesa";
        break;
      case DUTCH:
        auctionTypeName = "Holandesa";
        break;
      case JAPANESE:
        auctionTypeName = "Japonesa";
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  public AuctionTypeEnum getAuctionTypeEnum() {
    return auctionTypeEnum;
  }

  public String getAuctionTypeName() {
    return auctionTypeName;
  }
}
