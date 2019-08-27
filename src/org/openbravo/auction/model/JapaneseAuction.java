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

public class JapaneseAuction extends Auction {
  protected BigDecimal maximumSalePrice;
  private Integer numberOfRounds;

  public JapaneseAuction(AuctionState auctionState, Date celebrationDate, Date deadLine,
      Integer numberOfRounds, Integer maximumBiddersNum, Item item, BigDecimal startingPrice,
      BigDecimal currentPrice, BigDecimal maximumSalePrice, String additionalInformation,
      TreeSet<JapaneseAuctionBuyer> japaneseAuctionBuyers) {
    super(new AuctionType(AuctionTypeEnum.JAPANESE), auctionState, celebrationDate, deadLine,
        maximumBiddersNum, item, startingPrice, currentPrice, additionalInformation,
        japaneseAuctionBuyers);

    this.maximumSalePrice = maximumSalePrice;
    this.numberOfRounds = numberOfRounds;
  }

  public BigDecimal getMaximumSalePrice() {
    return maximumSalePrice;
  }

  public void setMaximumSalePrice(BigDecimal maximumSalePrice) {
    this.maximumSalePrice = maximumSalePrice;
  }

  public Integer getNumberOfRounds() {
    return numberOfRounds;
  }

  public void setNumberOfRounds(Integer numberOfRounds) {
    this.numberOfRounds = numberOfRounds;
  }

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nPrecio de salida: "
        + startingPrice + " €\nProducto subastado:\n" + item + "\nInformación adicional: "
        + additionalInformation;
  }
}
