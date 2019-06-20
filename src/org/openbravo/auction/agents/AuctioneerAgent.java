package org.openbravo.auction.agents;

import org.openbravo.auction.model.Auction;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class AuctioneerAgent extends Agent {

  private Auction auction;

  public AuctioneerAgent(Auction auction) {
    this.auction = auction;
  }

  @Override
  public void setup() {
    DFAgentDescription dfAgentDescription = new DFAgentDescription();

    dfAgentDescription.setName(getAID());

    ServiceDescription serviceDescription = new ServiceDescription();

    serviceDescription.setType("AUCTIONEER");
    serviceDescription.setName(this.getLocalName());
    dfAgentDescription.addServices(serviceDescription);

    try {
      DFService.register(this, dfAgentDescription);
    } catch (FIPAException fe) {
      fe.printStackTrace();
    }
  }

  @Override
  public void takeDown() {
    System.out.println("AUCTIONEER agent " + getAID().getName() + " terminating.");
  }

}
