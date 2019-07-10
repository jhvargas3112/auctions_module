package org.openbravo.auction.agents;

import org.openbravo.auction.model.Auction;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

// DE MOMENTO SE QUEDA, PERO CREO QUE SOBRA EN LA JERARQUÍA.

@SuppressWarnings("serial")
public class AuctioneerAgent extends OpenbravoAgent {

  private Auction auction;

  public AuctioneerAgent(Auction auction) {
    this.auction = auction;
  }

  @Override
  public void setup() {
  }

  @Override
  public void takeDown() {
    System.out.println("AUCTIONEER agent " + getAID().getName() + " terminating.");
  }

}
