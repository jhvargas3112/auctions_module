package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AuctionInfoRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get("json")
  public Auction getAuctionInfo() {
    Integer auctionCode = Integer.parseInt(getQueryValue("auction_id"));

    Auction auction = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionCode)) {
        auction = ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionCode);
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }

    return auction;
  }
}
