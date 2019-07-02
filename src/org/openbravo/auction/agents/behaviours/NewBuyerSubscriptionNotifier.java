package org.openbravo.auction.agents.behaviours;

import org.openbravo.auction.agents.BuyerAgent;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class NewBuyerSubscriptionNotifier extends OneShotBehaviour {

  @Override
  public void action() {
    DFAgentDescription[] openbravoAuctionAgents = ((BuyerAgent) myAgent)
        .searchAgent("OPENBRAVO-AUCTION");

    if (openbravoAuctionAgents != null && openbravoAuctionAgents.length > 0) {
      ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
      String buyerEmail = ((BuyerAgent) myAgent).getBuyerEmail();
      msg.setContent(buyerEmail);

      msg.addReceiver(openbravoAuctionAgents[0].getName());

      myAgent.send(msg);

      System.out.println("Se ha informado a la casa de subastas que el comprador " + buyerEmail
          + " se ha inscrito a la subasta.");
    }
  }

}
