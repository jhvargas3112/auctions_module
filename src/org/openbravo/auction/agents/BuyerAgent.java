package org.openbravo.auction.agents;

import org.openbravo.auction.agents.behaviours.NewBuyerSubscriptionNotifier;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class BuyerAgent extends OpenbravoAgent {
  String buyerEmail;

  @Override
  public void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      buyerEmail = (String) args[0];
      registerAgent("BUYER", this.getLocalName());
    }

    addBehaviour(new NewBuyerSubscriptionNotifier());
  }

  @Override
  public void takeDown() {
    System.out.println("Seller-agent " + getAID().getName() + " terminating.");
  }

  public String getBuyerEmail() {
    return buyerEmail;
  }

  public void setBuyerEmail(String buyerEmail) {
    this.buyerEmail = buyerEmail;
  }

}
