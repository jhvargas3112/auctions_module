package org.openbravo.auction.service;

import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.Item;

public interface OpenbravoAuctionService {

  public Auction createAuction(String auctionType, Date celebrationDate, Date deadLine,
      Integer maximumBiddersNum, Item item, Double startingPrice, Double minimumSalePrice,
      String additionalInformation);

  public void publishAuction(JSONObject auctionParameters);

  /**
   * Sends an email to bidders with the information of the new published auction. The bidders emails
   * are picked from the config.properties file.
   */
  public void notifyBidders(ArrayList<String> newAuctionNotificationMessageElements);

  /**
   * Sends an email to bidders with the information of the new published auction.
   * 
   * @param receivers
   *          - an String array with the bidders emails.
   */
  public void notifyBidders(String[] receivers,
      ArrayList<String> newAuctionNotificationMessageElements);

  public String createNewAuctionNotificationMessage(
      ArrayList<String> newAuctionNotificationMessageElements, String receiverEmail);

  public void registerBuyerToAuction(String email);

}
