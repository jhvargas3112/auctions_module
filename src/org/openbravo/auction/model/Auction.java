package org.openbravo.auction.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeSet;

import org.openbravo.auction.utils.AuctionState;
import org.openbravo.auction.utils.AuctionType;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public abstract class Auction {
  protected final AuctionType auctionType;
  protected AuctionState auctionState;

  protected Date celebrationDate;
  protected Integer maximumBiddersNum;
  protected Item item;
  protected BigDecimal startingPrice;
  protected BigDecimal minimumSalePrice;
  protected String additionalInformation;

  protected TreeSet<?> auctionBuyers;

  public Auction(AuctionType auctionType, AuctionState auctionState, Date celebrationDate,
      Integer maximumBiddersNum, Item item, BigDecimal startingPrice, BigDecimal minimumSalePrice,
      String additionalInformation, TreeSet<?> auctionBuyers) {
    this.auctionType = auctionType;
    this.auctionState = auctionState;

    this.celebrationDate = celebrationDate;
    this.maximumBiddersNum = maximumBiddersNum;
    this.item = item;
    this.startingPrice = startingPrice;
    this.minimumSalePrice = minimumSalePrice;
    this.additionalInformation = additionalInformation;

    this.auctionBuyers = auctionBuyers;
  }

  public AuctionType getAuctionType() {
    return auctionType;
  }

  public AuctionState getAuctionState() {
    return auctionState;
  }

  public void setAuctionState(AuctionState auctionState) {
    this.auctionState = auctionState;
  }

  public Date getCelebrationDate() {
    return celebrationDate;
  }

  public void setCelebrationDate(Date celebrationDate) {
    this.celebrationDate = celebrationDate;
  }

  public Integer getMaximumBiddersNum() {
    return maximumBiddersNum;
  }

  public void setMaximumBiddersNum(Integer maximumBiddersNum) {
    this.maximumBiddersNum = maximumBiddersNum;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public BigDecimal getStartingPrice() {
    return startingPrice;
  }

  public void setStartingPrice(BigDecimal startingPrice) {
    this.startingPrice = startingPrice;
  }

  public BigDecimal getMinimumSalePrice() {
    return minimumSalePrice;
  }

  public void setMinimumSalePrice(BigDecimal minimumSalePrice) {
    this.minimumSalePrice = minimumSalePrice;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public TreeSet<?> getAuctionBuyers() {
    return auctionBuyers;
  }

  public void setAuctionBuyers(TreeSet<?> auctionBuyers) {
    this.auctionBuyers = auctionBuyers;
  }
}
