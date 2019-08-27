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

public class IncrementItemPrice extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post
  public BigDecimal incrementItemPrice(Representation amountToIncrementRepresentation) {
    String auctionId = getQueryValue("auction_id");
    BigDecimal amountToIncrement = null;

    try {
      amountToIncrement = new BigDecimal(
          Double.parseDouble(amountToIncrementRepresentation.getText().toString()));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    BigDecimal priceAfterTheIncrement = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        Auction auction = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);

        priceAfterTheIncrement = auction.getStartingPrice().add(amountToIncrement);

        auction.setStartingPrice(priceAfterTheIncrement);
        auction.setCurrentPrice(priceAfterTheIncrement);

        ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .put(auctionId.toString(), auction);
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }

    return priceAfterTheIncrement;
  }
}
