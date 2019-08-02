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
      Integer auctionId);

  /**
   * Sends an email to bidders with the information of the new published auction.
   * 
   * @param receivers
   *          - an String array with the bidders emails.
   */
  public void notifyBidders(String[] receivers,
      ArrayList<String> newAuctionNotificationMessageElements, Integer auctionId);

  public String createNewAuctionNotificationMessage(
      ArrayList<String> newAuctionNotificationMessageElements, Integer auctionId,
      String receiverEmail);

  public String createNewSubscriptionNotificationMessage(Integer auctionId, String receiverEmail);

  public void notifySubscription(Integer auctionId, String buyerEmail);

  public void startAuctionCelebration(Integer auctionId);

  public void cancelAuctionCelebration(Integer auctionId);

  public Auction getAuction(Integer auctionId);

  public Integer countAuctionBuyers(Integer auctionId);

  public Boolean isBuyerAlreadySubscribed();

  public void changeAuctionState(Integer auctionId, AuctionStateEnum auctionState);

}
