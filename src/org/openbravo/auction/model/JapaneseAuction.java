package org.openbravo.auction.model;

import java.util.Date;

public class JapaneseAuction extends Auction {

  public JapaneseAuction(Item item, Date celebrationDate, Date deadLine, Integer maximumBiddersNum,
      Double startingPrice, Double minimumSalePrice, String description,
      String additionalInformation) {
    super(item, celebrationDate, deadLine, maximumBiddersNum, startingPrice, minimumSalePrice,
        description, additionalInformation);
    // TODO Auto-generated constructor stub
  }

}
