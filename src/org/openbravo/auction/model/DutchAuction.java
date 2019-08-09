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
  protected BigDecimal minimumSalePrice;
  private Integer numberOfRounds;

  public DutchAuction(AuctionState auctionState, Date celebrationDate, Date deadLine,
      Integer numberOfRounds, Integer maximumBiddersNum, Item item, BigDecimal startingPrice,
      BigDecimal currentPrice, BigDecimal minimumSalePrice, String additionalInformation,
      TreeSet<DutchAuctionBuyer> dutchAuctionBuyers) {
    super(new AuctionType(AuctionTypeEnum.DUTCH), auctionState, celebrationDate, deadLine,
        maximumBiddersNum, item, startingPrice, currentPrice, additionalInformation,
        dutchAuctionBuyers);

    this.minimumSalePrice = minimumSalePrice;
    this.numberOfRounds = numberOfRounds;
  }

  public BigDecimal getMinimumSalePrice() {
    return minimumSalePrice;
  }

  public void setMinimumSalePrice(BigDecimal minimumSalePrice) {
    this.minimumSalePrice = minimumSalePrice;
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
        + "\nPrecio de salida: " + startingPrice + " €\nProducto subastado:\n" + item
        + "\nInformación adicional: " + additionalInformation;
  }
}
