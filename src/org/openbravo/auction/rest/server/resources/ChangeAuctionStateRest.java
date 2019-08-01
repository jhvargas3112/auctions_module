package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.AuctionState;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ChangeAuctionStateRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post
  public void changeAuctionState() {
    Integer auctionCode = Integer.parseInt(getQueryValue("auction_id"));
    AuctionState auctionState = new AuctionState(
        AuctionStateEnum.valueOf(getQueryValue("auction_state")));

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionCode)) {
        ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions")).get(auctionCode)
            .setAuctionState(auctionState);
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }
  }
}
