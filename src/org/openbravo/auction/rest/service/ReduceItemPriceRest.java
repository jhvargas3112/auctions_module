package org.openbravo.auction.rest.service;

import org.openbravo.auction.model.Auction;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ReduceItemPriceRest extends ServerResource {

  @Post("json")
  public Double decreaseCurrentPrice(Double amountToReduceOn) {
    Auction auction = (Auction) getContext().getAttributes().get("auction");
    double priceAfterTheDecrement = auction.getStartingPrice() - amountToReduceOn;
    auction.setStartingPrice(priceAfterTheDecrement);

    getContext().getAttributes().put("auction", auction);

    return priceAfterTheDecrement;
  }

}
