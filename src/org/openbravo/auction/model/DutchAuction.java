package org.openbravo.auction.model;

import java.util.Date;

public class DutchAuction extends Auction {

  public DutchAuction(Item item, Date celebrationDate, Date deadLine, Integer maximumBiddersNum,
      Double startingPrice, Double minimumSalePrice, String description,
      String additionalInformation) {
    super(item, celebrationDate, deadLine, maximumBiddersNum, startingPrice, minimumSalePrice,
        description, additionalInformation);
  }

}
