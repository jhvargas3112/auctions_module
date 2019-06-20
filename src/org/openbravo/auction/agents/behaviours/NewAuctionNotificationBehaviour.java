package org.openbravo.auction.agents.behaviours;

import java.util.ArrayList;

import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;

import jade.core.behaviours.OneShotBehaviour;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class NewAuctionNotificationBehaviour extends OneShotBehaviour {
  private ArrayList<String> newAuctionNotificationMessageElements;

  public NewAuctionNotificationBehaviour(ArrayList<String> newAuctionNotificationMessageElements) {
    this.newAuctionNotificationMessageElements = newAuctionNotificationMessageElements;
  }

  @Override
  public void action() {
    new OpenbravoAuctionServiceImpl().notifyBidders(newAuctionNotificationMessageElements);
  }

}
