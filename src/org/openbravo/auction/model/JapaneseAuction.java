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

public class JapaneseAuction extends Auction {
  protected Integer numberOfRounds;

  protected TreeSet<JapaneseAuctionBuyer> auctionBuyers;

  public JapaneseAuction(AuctionState auctionState, Date celebrationDate, Integer numberOfRounds,
      Integer maximumBiddersNum, Item item, Double startingPrice, Double minimumSalePrice,
      String additionalInformation) {
    super(new AuctionType(AuctionTypeEnum.JAPANESE), auctionState, celebrationDate,
        maximumBiddersNum, item, startingPrice, minimumSalePrice, additionalInformation);

    this.numberOfRounds = numberOfRounds;

    auctionBuyers = new TreeSet<JapaneseAuctionBuyer>();
  }

  public Integer getNumberOfRounds() {
    return numberOfRounds;
  }

  public void setNumberOfRounds(Integer numberOfRounds) {
    this.numberOfRounds = numberOfRounds;
  }

  public TreeSet<JapaneseAuctionBuyer> getAuctionBuyers() {
    return auctionBuyers;
  }

  public void setAuctionBuyers(TreeSet<JapaneseAuctionBuyer> auctionBuyers) {
    this.auctionBuyers = auctionBuyers;
  }

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nPrecio de salida: "
        + startingPrice + "\nProducto subastado:\n" + item + "\nInformación adicional: "
        + additionalInformation;
  }
}
