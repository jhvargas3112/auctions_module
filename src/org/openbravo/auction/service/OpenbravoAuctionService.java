package org.openbravo.auction.service;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONObject;

public interface OpenbravoAuctionService {
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
