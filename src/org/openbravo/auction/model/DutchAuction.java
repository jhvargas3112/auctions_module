package org.openbravo.auction.model;

import java.math.BigDecimal;
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
  private Integer numberOfRounds;

  public DutchAuction(AuctionType auctionType, AuctionState auctionState, Date celebrationDate,
      Date deadLine, Integer maximumBiddersNum, Item item, BigDecimal startingPrice,
      BigDecimal minimumSalePrice, String additionalInformation,
      TreeSet<DutchAuctionBuyer> dutchAuctionBuyers) {
    super(auctionType, auctionState, celebrationDate, deadLine, maximumBiddersNum, item,
        startingPrice, minimumSalePrice, additionalInformation, dutchAuctionBuyers);
  }

  public DutchAuction(AuctionState auctionState, Date celebrationDate, Date deadLine,
      Integer numberOfRounds, Integer maximumBiddersNum, Item item, BigDecimal startingPrice,
      BigDecimal minimumSalePrice, String additionalInformation,
      TreeSet<DutchAuctionBuyer> dutchAuctionBuyers) {
    super(new AuctionType(AuctionTypeEnum.DUTCH), auctionState, celebrationDate, deadLine,
        maximumBiddersNum, item, startingPrice, minimumSalePrice, additionalInformation,
        dutchAuctionBuyers);

    this.deadLine = deadLine;
    this.numberOfRounds = numberOfRounds;
  }

  public DutchAuction(AuctionType auctionType, AuctionState auctionState, Date celebrationDate,
      Date deadLine, Integer numberOfRounds, Integer maximumBiddersNum, Item item,
      BigDecimal startingPrice, BigDecimal minimumSalePrice, String additionalInformation,
      TreeSet<DutchAuctionBuyer> dutchAuctionBuyers) {
    super(auctionType, auctionState, celebrationDate, deadLine, maximumBiddersNum, item,
        startingPrice, minimumSalePrice, additionalInformation, dutchAuctionBuyers);

    this.deadLine = deadLine;
    this.numberOfRounds = numberOfRounds;
  }

  @Override
  public Date getDeadLine() {
    return deadLine;
  }

  @Override
  public void setDeadLine(Date deadLine) {
    this.deadLine = deadLine;
  }

  public Integer getNumberOfRounds() {
    return numberOfRounds;
  }

  public void setNumberOfRounds(Integer numberOfRounds) {
    this.numberOfRounds = numberOfRounds;
  }

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nFecha de cierre: " + deadLine
        + "\nPrecio de salida: " + startingPrice + "\nProducto subastado:\n" + item
        + "\nInformación adicional: " + additionalInformation;
  }
}
