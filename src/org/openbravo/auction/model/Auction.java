package org.openbravo.auction.model;

import java.util.Date;

import org.openbravo.auction.utils.AuctionType;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public abstract class Auction {

  protected final AuctionType auctionType;

  protected Date celebrationDate;
  protected Integer maximumBiddersNum;
  protected Item item;
  protected Double startingPrice;
  protected Double minimumSalePrice;
  protected String additionalInformation;

  public Auction(AuctionType auctionType, Date celebrationDate, Integer maximumBiddersNum,
      Item item, Double startingPrice, Double minimumSalePrice, String additionalInformation) {
    this.auctionType = auctionType;
    this.celebrationDate = celebrationDate;
    this.maximumBiddersNum = maximumBiddersNum;
    this.item = item;
    this.startingPrice = startingPrice;
    this.minimumSalePrice = minimumSalePrice;
    this.additionalInformation = additionalInformation;
  }

  public AuctionType getAuctionType() {
    return auctionType;
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

  public Double getStartingPrice() {
    return startingPrice;
  }

  public void setStartingPrice(Double startingPrice) {
    this.startingPrice = startingPrice;
  }

  public Double getMinimumSalePrice() {
    return minimumSalePrice;
  }

  public void setMinimumSalePrice(Double minimumSalePrice) {
    this.minimumSalePrice = minimumSalePrice;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

}
