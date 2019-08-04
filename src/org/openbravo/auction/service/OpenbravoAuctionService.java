package org.openbravo.auction.service;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.AuctionStateEnum;

public interface OpenbravoAuctionService {
  public void publishAuction(JSONObject auctionParameters);

  /**
   * Sends an email to bidders with the information of the new published auction. The bidders emails
   * are picked from the config.properties file.
   */
  public void notifyBidders(ArrayList<String> newAuctionNotificationMessageElements,
      String auctionId);

  /**
   * Sends an email to bidders with the information of the new published auction.
   * 
   * @param receivers
   *          - an String array with the bidders emails.
   */
  public void notifyBidders(String[] receivers,
      ArrayList<String> newAuctionNotificationMessageElements, String auctionId);

  public String createNewAuctionNotificationMessage(
      ArrayList<String> newAuctionNotificationMessageElements, String auctionId,
      String receiverEmail);

  public String createNewSubscriptionNotificationMessage(String auctionId, String buyerId,
      String receiverEmail);

  public void notifySubscription(String auctionId, String buyerId, String buyerEmail);

  public void startAuctionCelebration(String auctionId);

  public void cancelAuctionCelebration(String auctionId);

  public Auction getAuction(String auctionId);

  public Integer countAuctionBuyers(String auctionId);

  public Boolean isBuyerAlreadySubscribed();

  public void changeAuctionState(String auctionId, AuctionStateEnum auctionState);

}
