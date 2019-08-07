package org.openbravo.auction.service;

import java.util.ArrayList;
import java.util.TreeSet;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.AuctionBuyer;
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
   * @param buyersEmails
   *          - an String array with the bidders buyers.
   */
  public void notifyBidders(String[] buyersEmails,
      ArrayList<String> newAuctionNotificationMessageElements, String auctionId);

  public void notifySubscription(String auctionId, String buyerId, String buyerEmail);

  public void notifyAuctionWinner(String auctionId, String buyerEmail);

  public String createNewAuctionNotificationMessage(
      ArrayList<String> newAuctionNotificationMessageElements, String auctionId, String buyerEmail);

  public String createNewSubscriptionNotificationMessage(String auctionId, String buyerId,
      String buyerEmail);

  public String createWinnerNotificationMessage(String auctionId, String buyerEmail);

  public void startAuctionCelebration(String auctionId);

  public void finishAuctionCelebration(String auctionId);

  public void cancelAuctionCelebration(String auctionId);

  public Auction getAuction(String auctionId);

  public TreeSet<AuctionBuyer> getAuctionBuyers(String auctionId);

  public Integer countAuctionBuyers(String auctionId);

  public void changeAuctionState(String auctionId, AuctionStateEnum auctionState);

  public void startOpenbravoAuctionRestServer();
}
