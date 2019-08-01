package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AuctionStateRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get
  public AuctionStateEnum getAuctionInfo() {
    Integer auctionCode = Integer.parseInt(getQueryValue("auction_id"));

    AuctionStateEnum auctionState = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionCode)) {
        auctionState = ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionCode)
            .getAuctionState()
            .getAuctionStateEnum();
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }

    return auctionState;
  }
}
