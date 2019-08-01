package org.openbravo.auction.model;

import java.util.Date;
import java.util.TreeSet;

import org.openbravo.auction.utils.AuctionState;
import org.openbravo.auction.utils.AuctionType;
import org.openbravo.auction.utils.AuctionTypeEnum;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class DutchAuction extends Auction {
  protected Date deadLine;
  protected Integer numberOfRounds;

  protected TreeSet<DutchAuctionBuyer> auctionBuyers;

  public DutchAuction(AuctionType auctionType, AuctionState auctionState, Date celebrationDate,
      Integer maximumBiddersNum, Item item, Double startingPrice, Double minimumSalePrice,
      String additionalInformation) {
    super(auctionType, auctionState, celebrationDate, maximumBiddersNum, item, startingPrice,
        minimumSalePrice, additionalInformation);

    auctionBuyers = new TreeSet<DutchAuctionBuyer>();
  }

  public DutchAuction(AuctionState auctionState, Date celebrationDate, Date deadLine,
      Integer numberOfRounds, Integer maximumBiddersNum, Item item, Double startingPrice,
      Double minimumSalePrice, String additionalInformation) {
    super(new AuctionType(AuctionTypeEnum.DUTCH), auctionState, celebrationDate, maximumBiddersNum,
        item, startingPrice, minimumSalePrice, additionalInformation);

    this.deadLine = deadLine;
    this.numberOfRounds = numberOfRounds;

    auctionBuyers = new TreeSet<DutchAuctionBuyer>();
  }

  public DutchAuction(AuctionType auctionType, AuctionState auctionState, Date celebrationDate,
      Date deadLine, Integer numberOfRounds, Integer maximumBiddersNum, Item item,
      Double startingPrice, Double minimumSalePrice, String additionalInformation) {
    super(auctionType, auctionState, celebrationDate, maximumBiddersNum, item, startingPrice,
        minimumSalePrice, additionalInformation);

    this.deadLine = deadLine;
    this.numberOfRounds = numberOfRounds;

    auctionBuyers = new TreeSet<DutchAuctionBuyer>();
  }

  public Date getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(Date deadLine) {
    this.deadLine = deadLine;
  }

  public Integer getNumberOfRounds() {
    return numberOfRounds;
  }

  public void setNumberOfRounds(Integer numberOfRounds) {
    this.numberOfRounds = numberOfRounds;
  }

  public TreeSet<DutchAuctionBuyer> getAuctionBuyers() {
    return auctionBuyers;
  }

  public void setAuctionBuyers(TreeSet<DutchAuctionBuyer> auctionBuyers) {
    this.auctionBuyers = auctionBuyers;
  }

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nFecha de cierre: " + deadLine
        + "\nPrecio de salida: " + startingPrice + "\nProducto subastado:\n" + item
        + "\nInformación adicional: " + additionalInformation;
  }

}
