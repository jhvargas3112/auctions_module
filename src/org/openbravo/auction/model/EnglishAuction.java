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

public class EnglishAuction extends Auction {
  protected Date deadLine;

  protected TreeSet<EnglishAuctionBuyer> auctionBuyers;

  public EnglishAuction(AuctionState auctionState, Date celebrationDate, Date deadLine,
      Integer maximumBiddersNum, Item item, Double startingPrice, Double minimumSalePrice,
      String additionalInformation) {
    super(new AuctionType(AuctionTypeEnum.ENGLISH), auctionState, celebrationDate,
        maximumBiddersNum, item, startingPrice, minimumSalePrice, additionalInformation);

    this.deadLine = deadLine;

    auctionBuyers = new TreeSet<EnglishAuctionBuyer>();
  }

  public Date getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(Date deadLine) {
    this.deadLine = deadLine;
  }

  public TreeSet<EnglishAuctionBuyer> getAuctionBuyers() {
    return auctionBuyers;
  }

  public void setAuctionBuyers(TreeSet<EnglishAuctionBuyer> auctionBuyers) {
    this.auctionBuyers = auctionBuyers;
  }

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nFecha de cierre: " + deadLine
        + "\nPrecio de salida: " + startingPrice + "\nProducto subastado:\n" + item
        + "\nInformación adicional: " + additionalInformation;
  }

}
