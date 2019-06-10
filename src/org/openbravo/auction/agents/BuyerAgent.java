package org.openbravo.auction.agents;

import jade.core.Agent;

public class BuyerAgent extends Agent {
  @Override
  public void setup() {

  }

  @Override
  public void takeDown() {
    System.out.println("Seller-agent " + getAID().getName() + " terminating.");
  }

  public void updateCatalogue(String title, Double price) {
  }

}
