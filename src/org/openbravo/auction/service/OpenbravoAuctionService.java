package org.openbravo.auction.service;

import java.util.Date;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;

public interface OpenbravoAuctionService {

  public Auction createAuction(String auctionType, Date celebrationDate, Date deadLine,
      Integer maximumBiddersNum, Double startingPrice, Double minimumSalePrice, String description,
      String additionalInformation);

  public void publishAuction(JSONObject auctionParameters);

}
