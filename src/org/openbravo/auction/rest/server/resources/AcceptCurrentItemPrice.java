package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.service.impl.DutchAuctionServiceImpl;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class AcceptCurrentItemPrice extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post
  public void acceptCurrentItemPrice() {
    String auctionId = getQueryValue("auction_id");
    String buyerId = getQueryValue("buyer_id");

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        new DutchAuctionServiceImpl().finishAuctionCelebration(auctionId, buyerId);
      }
    }
  }
}
