package org.openbravo.auction.rest.service;

import org.openbravo.auction.agents.OpenbravoAuctionAgentContainer;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class NewAuctionOfferRest extends ServerResource {

  @Post
  public void getAuctionInfo(String email) {
    AgentController buyerAgent = null;

    try {
      buyerAgent = OpenbravoAuctionAgentContainer.INSTANCE.getValue().getAgent(email);
    } catch (ControllerException e) {
      e.printStackTrace();
    }

    // buyerAgent;
  }

}
