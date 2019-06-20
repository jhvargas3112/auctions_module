package org.openbravo.auction.model;

import java.util.Date;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class EnglishAuction extends Auction {

  protected Date deadLine;

  public EnglishAuction(Date celebrationDate, Date deadLine, Integer maximumBiddersNum, Item item,
      Double startingPrice, Double minimumSalePrice, String additionalInformation) {
    super(celebrationDate, maximumBiddersNum, item, startingPrice, minimumSalePrice,
        additionalInformation);

    this.deadLine = deadLine;
  }

  public Date getDeadLine() {
    return deadLine;
  }

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
