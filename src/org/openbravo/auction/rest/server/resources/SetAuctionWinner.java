package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class SetAuctionWinner extends ServerResource {
  @SuppressWarnings("unchecked")
  @Put
  public void setAuctionWinner(String winnerEmail) {
    String auctionId = getQueryValue("auction_id");

    HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
        .get("auctions");

    auctions.get(auctionId).setWinnerEmail(winnerEmail);
  }
}
