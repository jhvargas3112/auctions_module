package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ReduceItemPriceRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post("json")
  public Double decreaseCurrentPrice() {
    Integer auctionCode = Integer.parseInt(getQueryValue("auction_id"));
    Double amountToReduce = Double.parseDouble(getQueryValue("amount_to_reduce"));

    Double priceAfterTheDecrement = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionCode)) {
        Auction auction = ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionCode);

        priceAfterTheDecrement = auction.getStartingPrice() - amountToReduce;

        auction.setStartingPrice(priceAfterTheDecrement);
        ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions")).put(auctionCode,
            auction);
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }

    return priceAfterTheDecrement;
  }
}
