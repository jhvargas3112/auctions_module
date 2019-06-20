package org.openbravo.auction.agents.behaviours;

import org.openbravo.auction.agents.BuyerAgent;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class NewBuyerSubscriptionNotifier extends OneShotBehaviour {

  @Override
  public void action() {
    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    msg.addReceiver(new AID("OPENBRAVO-AUCTION", AID.ISLOCALNAME));

    String buyerEmail = ((BuyerAgent) getAgent()).getBuyerEmail();

    msg.setContent(buyerEmail);

    myAgent.send(msg);

    System.out.println("Se ha informado a la casa de subastas que el comprador " + buyerEmail
        + " se ha inscrito a la subasta.");
  }

}
