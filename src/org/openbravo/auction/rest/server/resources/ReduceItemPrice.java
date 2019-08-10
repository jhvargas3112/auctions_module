package org.openbravo.auction.rest.server.resources;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ReduceItemPrice extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post
  public BigDecimal decreaseCurrentPrice(Representation amountToReduceRepresentation) {
    String auctionId = getQueryValue("auction_id");
    BigDecimal amountToReduce = null;

    try {
      amountToReduce = new BigDecimal(
          Double.parseDouble(amountToReduceRepresentation.getText().toString()));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    BigDecimal priceAfterTheDecrement = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        Auction auction = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);

        priceAfterTheDecrement = auction.getStartingPrice().subtract(amountToReduce);

        auction.setStartingPrice(priceAfterTheDecrement);
        auction.setCurrentPrice(priceAfterTheDecrement);

        ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .put(auctionId.toString(), auction);
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }

    return priceAfterTheDecrement;
  }
}
