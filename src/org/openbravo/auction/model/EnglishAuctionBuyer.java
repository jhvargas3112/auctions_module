package org.openbravo.auction.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class EnglishAuctionBuyer extends AuctionBuyer implements Comparable<EnglishAuctionBuyer> {
  private BigDecimal lastOffer;

  public EnglishAuctionBuyer(Integer id, String email, BigDecimal lastOffer) {
    super(id, email);
    this.lastOffer = lastOffer;
  }

  public BigDecimal getLastOffer() {
    return lastOffer;
  }

  public void setLastOffer(BigDecimal lastOffer) {
    this.lastOffer = lastOffer;
  }

  @Override
  public boolean equals(EnglishAuctionBuyer englishAuctionBuyer) {
    return !StringUtils.equals(this.email, englishAuctionBuyer.getEmail())
        && !this.id.equals(englishAuctionBuyer.getId());
  }

  @Override
  public int compareTo(EnglishAuctionBuyer englishAuctionBuyer) {
    if (!this.lastOffer.equals(new BigDecimal(0.0))) {
      return this.lastOffer.compareTo(englishAuctionBuyer.getLastOffer());
    } else {
      return 1;
    }
  }
}
