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

  // TODO: Aquí alomejor hace falta una atributo para la frecuencia de actualización del precio
  // actual de
  // la subasta.

  public JapaneseAuction(Date celebrationDate, Integer maximumBiddersNum, Item item,
      Double startingPrice, Double minimumSalePrice, String additionalInformation) {
    super(new AuctionType(AuctionTypeEnum.JAPANESE), celebrationDate, maximumBiddersNum, item, startingPrice,
        minimumSalePrice, additionalInformation);
  }

  @Override
  public String toString() {
    return "Fecha y hora de celebración: " + celebrationDate + "\nPrecio de salida: "
        + startingPrice + "\nProducto subastado:\n" + item + "\nInformación adicional: "
        + additionalInformation;
  }

}
