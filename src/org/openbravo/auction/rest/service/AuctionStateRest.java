package org.openbravo.auction.rest.service;

import org.openbravo.auction.utils.AuctionState;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AuctionStateRest extends ServerResource {

  @Get
  public AuctionState getAuctionInfo() {
    return (AuctionState) getContext().getAttributes().get("auctionState");
  }

}
