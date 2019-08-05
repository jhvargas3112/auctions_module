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

public class EnglishAuction extends Auction {
  public EnglishAuction(AuctionState auctionState, Date celebrationDate, Date deadLine,
      Integer maximumBiddersNum, Item item, BigDecimal startingPrice, BigDecimal minimumSalePrice,
      String additionalInformation, TreeSet<EnglishAuctionBuyer> englishAuctionBuyers) {
    super(new AuctionType(AuctionTypeEnum.ENGLISH), auctionState, celebrationDate, deadLine,
        maximumBiddersNum, item, startingPrice, minimumSalePrice, additionalInformation,
        englishAuctionBuyers);

    this.deadLine = deadLine;
  }

  @Override
  public Date getDeadLine() {
    return deadLine;
  }

  @Override
  public void setDeadLine(Date deadLine) {
    this.deadLine = deadLine;
  }

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nFecha de cierre: " + deadLine
        + "\nPrecio de salida: " + startingPrice + "\nProducto subastado:\n" + item
        + "\nInformación adicional: " + additionalInformation;
  }
}
