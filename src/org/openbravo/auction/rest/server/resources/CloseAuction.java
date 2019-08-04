package org.openbravo.auction.rest.server.resources;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class CloseAuction extends ServerResource {

  @Post
  public void closeAuction() {
    // OpenbravoAuctionAgentContainer.INSTANCE.getValue().getAgent(getDescription());
  }

}
