package org.openbravo.auction.agents;

import org.openbravo.auction.model.DutchAuction;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class DutchAuctioneerAgent extends OpenbravoAgent {
  private DutchAuction dutchAuction;

  @Override
  public void setup() {
  }

  @Override
  protected void takeDown() {
  }

  public DutchAuction getAuction() {
    return dutchAuction;
  }

  public void setAuction(DutchAuction auction) {
    this.dutchAuction = auction;
  }

}
