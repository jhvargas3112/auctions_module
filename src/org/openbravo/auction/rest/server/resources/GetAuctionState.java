package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GetAuctionState extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get
  public AuctionStateEnum getAuctionInfo() {
    String auctionCode = getQueryValue("auction_id");

    AuctionStateEnum auctionState = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionCode)) {
        auctionState = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
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
