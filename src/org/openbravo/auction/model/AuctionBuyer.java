package org.openbravo.auction.model;

public abstract class AuctionBuyer {
  protected String id;
  protected String email;

  public AuctionBuyer(String id, String email) {
    this.id = id;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public abstract boolean equals(EnglishAuctionBuyer englishAuctionBuyer);
}
