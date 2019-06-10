package org.openbravo.auction.model;

import java.util.Date;

public abstract class Auction {

  protected Item item;
  protected Date celebrationDate; // TODO: VOY A TENER AQUÍ QUE UNIR celebrationDate y
                                  // celebrationTime que vienen del cliente.
  protected Date deadLine; // TODO: VOY A TENER AQUÍ QUE UNIR deadLine y closingTime que vienen del
                           // cliente.
  protected Integer maximumBiddersNum;
  protected Double startingPrice;
  protected Double minimumSalePrice;
  protected String description;
  protected String additionalInformation;

  public Auction(Item item, Date celebrationDate, Date deadLine, Integer maximumBiddersNum,
      Double startingPrice, Double minimumSalePrice, String description,
      String additionalInformation) {
    this.item = item;
    this.celebrationDate = celebrationDate;
    this.deadLine = deadLine;
    this.maximumBiddersNum = maximumBiddersNum;
    this.startingPrice = startingPrice;
    this.minimumSalePrice = minimumSalePrice;
    this.description = description;
    this.additionalInformation = additionalInformation;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

}
