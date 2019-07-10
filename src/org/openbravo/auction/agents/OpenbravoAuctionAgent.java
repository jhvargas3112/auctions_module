package org.openbravo.auction.agents;

import java.util.ArrayList;

import org.openbravo.auction.agents.behaviours.NewAuctionNotificationBehaviour;
import org.openbravo.auction.agents.behaviours.SubscribeBuyerBehaviour;
import org.openbravo.auction.agents.behaviours.startOpenbravoAuctionRestServerBehaviour;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.utils.AuctionState;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class OpenbravoAuctionAgent extends OpenbravoAgent {

  private OpenbravoAuctionService openbravoAuctionService;

  private Auction auction;
  private AgentController auctioneerAgentController;
  private AuctionState auctionState = AuctionState.PUBLISHED; // FIXME: CREO QUE SOBRA.

  @Override
  public void setup() {
    registerAgent("OPENBRAVO-AUCTION", this.getLocalName());

    Object[] args = getArguments();
    Object[] auctioneerAgentAurguments = null;

    if (args != null && args.length > 0) {
      auction = (Auction) args[0];

      auctioneerAgentAurguments = new Object[1];
      auctioneerAgentAurguments[0] = auction;
    }

    try {
      if (auction instanceof EnglishAuction) {
        auctioneerAgentController = getContainerController().createNewAgent("english-auctioneer",
            "org.openbravo.auction.agents.EnglishAuctioneerAgent", auctioneerAgentAurguments);
      } else if (auction instanceof DutchAuction) {
        auctioneerAgentController = getContainerController().createNewAgent("dutch-auctioneer",
            "org.openbravo.auction.agents.DutchAuctioneerAgent", auctioneerAgentAurguments);
      } else if (auction instanceof JapaneseAuction) {
        auctioneerAgentController = getContainerController().createNewAgent("japanese-auctioneer",
            "org.openbravo.auction.agents.JapaneseAuctioneerAgent", auctioneerAgentAurguments);
      }
    } catch (StaleProxyException e) {
      e.printStackTrace();
    }

    ArrayList<String> newAuctionNotificationMessageElements = new ArrayList<String>();
    newAuctionNotificationMessageElements.add(auction.toString());

    addBehaviour(new startOpenbravoAuctionRestServerBehaviour(auction));
    addBehaviour(new NewAuctionNotificationBehaviour(newAuctionNotificationMessageElements));
    addBehaviour(new SubscribeBuyerBehaviour(auction.getCelebrationDate()));
  }

  @Override
  protected void takeDown() {
    // Deregister from the DF.
    try {
      DFService.deregister(this);
    } catch (FIPAException fe) {
      fe.printStackTrace();
    }

    System.out.println("OPENBRAVO-AUCTION agent " + getAID().getName() + " terminating.");
  }

  public Auction getAuction() {
    return auction;
  }

  public void setAuction(Auction auction) {
    this.auction = auction;
  }

  public AgentController getAuctioneerAgentController() {
    return auctioneerAgentController;
  }

  public void setAuctioneerAgentController(AgentController auctioneerAgentController) {
    this.auctioneerAgentController = auctioneerAgentController;
  }

}
