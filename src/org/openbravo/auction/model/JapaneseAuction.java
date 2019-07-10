package org.openbravo.auction.model;

import java.util.Date;

import org.openbravo.auction.utils.AuctionType;
import org.openbravo.auction.utils.AuctionTypeEnum;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class JapaneseAuction extends Auction {
  protected Integer numberOfRounds;

  public JapaneseAuction(Date celebrationDate, Integer numberOfRounds, Integer maximumBiddersNum,
      Item item, Double startingPrice, Double minimumSalePrice, String additionalInformation) {
    super(new AuctionType(AuctionTypeEnum.JAPANESE), celebrationDate, maximumBiddersNum, item,
        startingPrice, minimumSalePrice, additionalInformation);

    this.numberOfRounds = numberOfRounds;
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
        + startingPrice + "\nProducto subastado:\n" + item + "\nInformación adicional: "
        + additionalInformation;
  }
}
