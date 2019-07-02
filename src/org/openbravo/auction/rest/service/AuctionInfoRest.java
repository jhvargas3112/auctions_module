package org.openbravo.auction.rest.service;

import org.openbravo.auction.model.Auction;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AuctionInfoRest extends ServerResource {

  @Get("json")
  public Auction getAuctionInfo() {
    return (Auction) getContext().getAttributes().get("auction");
  }

}
