package org.openbravo.auction.model;

import java.util.Date;

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

  public DutchAuction(Date celebrationDate, Date deadLine, Integer numberOfRounds,
      Integer maximumBiddersNum, Item item, Double startingPrice, Double minimumSalePrice,
      String additionalInformation) {
    super(new AuctionType(AuctionTypeEnum.DUTCH), celebrationDate, maximumBiddersNum, item,
        startingPrice, minimumSalePrice, additionalInformation);

    this.deadLine = deadLine;
    this.numberOfRounds = numberOfRounds;
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

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nFecha de cierre: " + deadLine
        + "\nPrecio de salida: " + startingPrice + "\nProducto subastado:\n" + item
        + "\nInformación adicional: " + additionalInformation;
  }

}
