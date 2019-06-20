package org.openbravo.auction.agents.behaviours;

import org.openbravo.auction.rest.OpenbravoAuctionRestApplication;

import jade.core.behaviours.OneShotBehaviour;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class startOpenbravoAuctionRestServerBehaviour extends OneShotBehaviour {

  @Override
  public void action() {
    OpenbravoAuctionRestApplication.startOpenbravoAuctionRestServer();
  }

}
