package org.openbravo.auction.agents;

import org.openbravo.auction.model.EnglishAuction;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class EnglishAuctioneerAgent extends OpenbravoAgent {
  private EnglishAuction englishAuction;

  @Override
  public void setup() {

    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      englishAuction = (EnglishAuction) args[0];
      registerAgent("ENGLISH-AUCTIONEER", this.getLocalName());
    }

  }

  @Override
  protected void takeDown() {
  }

  public EnglishAuction getAuction() {
    return englishAuction;
  }

  public void setAuction(EnglishAuction auction) {
    this.englishAuction = auction;
  }

}
