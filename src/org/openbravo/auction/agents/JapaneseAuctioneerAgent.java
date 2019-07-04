package org.openbravo.auction.agents;

import org.openbravo.auction.model.JapaneseAuction;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class JapaneseAuctioneerAgent extends OpenbravoAgent {
  private JapaneseAuction japaneseAuction;

  @Override
  public void setup() {
  }

  @Override
  protected void takeDown() {
  }

  public JapaneseAuction getAuction() {
    return japaneseAuction;
  }

  public void setAuction(JapaneseAuction auction) {
    this.japaneseAuction = auction;
  }

}
