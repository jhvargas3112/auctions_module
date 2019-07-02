package org.openbravo.auction.agents.behaviours;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.rest.OpenbravoAuctionRestApplication;

import jade.core.behaviours.OneShotBehaviour;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class startOpenbravoAuctionRestServerBehaviour extends OneShotBehaviour {

  private Auction auction;

  public startOpenbravoAuctionRestServerBehaviour(Auction auction) {
    this.auction = auction;
  }

  @Override
  public void action() {
    OpenbravoAuctionRestApplication.startOpenbravoAuctionRestServer(auction);
  }

}
