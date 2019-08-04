package org.openbravo.auction.model;

public class DutchAuctionBuyer extends AuctionBuyer implements Comparable<DutchAuctionBuyer> {
  public DutchAuctionBuyer(String id, String email) {
    super(id, email);
  }

  @Override
  public int compareTo(DutchAuctionBuyer dutchAuctionBuyer) {
    return this.email.compareTo(dutchAuctionBuyer.email);
  }

  @Override
  public boolean equals(EnglishAuctionBuyer englishAuctionBuyer) {
    // TODO Auto-generated method stub
    return false;
  }
}
