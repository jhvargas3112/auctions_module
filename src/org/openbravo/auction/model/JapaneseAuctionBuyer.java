package org.openbravo.auction.model;

public class JapaneseAuctionBuyer extends AuctionBuyer implements Comparable<JapaneseAuctionBuyer> {
  public JapaneseAuctionBuyer(String id, String email) {
    super(id, email);
  }

  @Override
  public int compareTo(JapaneseAuctionBuyer japaneseAuctionBuyer) {
    return this.email.compareTo(japaneseAuctionBuyer.email);
  }

  @Override
  public boolean equals(EnglishAuctionBuyer englishAuctionBuyer) {
    return false;
  }
}
