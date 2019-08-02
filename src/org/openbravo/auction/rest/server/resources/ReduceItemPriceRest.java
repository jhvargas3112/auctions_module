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

public class ReduceItemPriceRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post
  public BigDecimal decreaseCurrentPrice(Representation amountToReduceRepresentation) {
    Integer auctionCode = Integer.parseInt(getQueryValue("auction_id"));
    BigDecimal amountToReduce = null;

    try {
      amountToReduce = new BigDecimal(
          Double.parseDouble(amountToReduceRepresentation.getText().toString()));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    BigDecimal priceAfterTheDecrement = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionCode)) {
        Auction auction = ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionCode);

        priceAfterTheDecrement = auction.getStartingPrice().subtract(amountToReduce);

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
