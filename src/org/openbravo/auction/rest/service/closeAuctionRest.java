package org.openbravo.auction.rest.service;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class closeAuctionRest extends ServerResource {

  @Post
  public void closeAuction() {
    // OpenbravoAuctionAgentContainer.INSTANCE.getValue().getAgent(getDescription());
  }

}
