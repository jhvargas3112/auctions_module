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
  protected Date deadLine;
  protected Integer maximumBiddersNum;
  protected Item item;
  protected BigDecimal startingPrice;
  protected BigDecimal currentPrice;
  protected String additionalInformation;

  protected TreeSet<?> auctionBuyers;

  public Auction(AuctionType auctionType, AuctionState auctionState, Date celebrationDate,
      Date deadLine, Integer maximumBiddersNum, Item item, BigDecimal startingPrice,
      BigDecimal currentPrice, String additionalInformation, TreeSet<?> auctionBuyers) {
    this.auctionType = auctionType;
    this.auctionState = auctionState;

    this.celebrationDate = celebrationDate;
    this.deadLine = deadLine;
    this.maximumBiddersNum = maximumBiddersNum;
    this.item = item;
    this.startingPrice = startingPrice;
    this.currentPrice = currentPrice;
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

  public Date getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(Date deadLine) {
    this.deadLine = deadLine;
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

  public BigDecimal getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(BigDecimal currentPrice) {
    this.currentPrice = currentPrice;
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
