package org.openbravo.auction.model;

public abstract class AuctionBuyer {
  protected Integer id;
  protected String email;

  public AuctionBuyer(Integer id, String email) {
    this.id = id;
    this.email = email;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
