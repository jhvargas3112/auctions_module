package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.AuctionState;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ChangeAuctionState extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post
  public void changeAuctionState(StringRepresentation auctionStateRepresentation) {
    String auctionId = getQueryValue("auction_id");
    AuctionState auctionState = new AuctionState(
        AuctionStateEnum.valueOf(auctionStateRepresentation.getText()));

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        ((HashMap<String, Auction>) getContext().getAttributes().get("auctions")).get(auctionId)
            .setAuctionState(auctionState);
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }
  }
}
