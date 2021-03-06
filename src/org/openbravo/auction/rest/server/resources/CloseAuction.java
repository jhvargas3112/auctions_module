package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

public class CloseAuction extends ServerResource {
  @SuppressWarnings("unchecked")
  @Delete
  public void closeAuction() {
    String auctionId = getQueryValue("auction_id");

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        auctions.remove(auctionId);
      }
    }
  }
}
