package org.openbravo.auction.agents;

import org.openbravo.auction.agents.behaviours.NewBuyerSubscriptionNotifier;

import jade.core.Agent;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class BuyerAgent extends Agent {
  String buyerEmail;

  @Override
  public void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      buyerEmail = (String) args[0];
    }

    addBehaviour(new NewBuyerSubscriptionNotifier());

  }

  @Override
  public void takeDown() {
    System.out.println("Seller-agent " + getAID().getName() + " terminating.");
  }

  public void updateCatalogue(String title, Double price) {
  }

  public String getBuyerEmail() {
    return buyerEmail;
  }

  public void setBuyerEmail(String buyerEmail) {
    this.buyerEmail = buyerEmail;
  }

}
