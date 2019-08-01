package org.openbravo.auction.rest.server.resources;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import jade.wrapper.AgentController;

public class NewBuyerOfferRest extends ServerResource {

  @Post
  public void getAuctionInfo(String email) {
    AgentController buyerAgent = null;
  }

}
