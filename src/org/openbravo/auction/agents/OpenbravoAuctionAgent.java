package org.openbravo.auction.agents;

import java.util.ArrayList;
import java.util.HashMap;

import org.openbravo.auction.agents.behaviours.NewAuctionNotificationBehaviour;
import org.openbravo.auction.agents.behaviours.NewBuyerSubscriptionBehaviour;
import org.openbravo.auction.agents.behaviours.startOpenbravoAuctionRestServerBehaviour;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.utils.AuctionState;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class OpenbravoAuctionAgent extends Agent {

  private OpenbravoAuctionService openbravoAuctionService;

  private Auction auction;
  private AgentController auctioneerAgentController;
  private HashMap<String, BuyerAgent> buyers; // Key: the buyer's email; Value: the JADE agent
                                              // associated to this buyer.
                                              // FIXME: CREO QUE BuyerAgent DEBERÍA SER UN TIPO
                                              // GENÉRICO AgentController
  private AuctionState auctionState = AuctionState.IN_SUSCRIPTION_TIME; // FIXME: CREO QUE SOBRA.
  // VALORARLO CON DETENIMIENTO.
  // CREO QUE HACE ALTA AGREGAR UN ATRIBUTO PARA ESPECIFICAR EL TIPO DE SUBASTA

  @Override
  public void setup() {
    DFAgentDescription dfAgentDescription = new DFAgentDescription();

    dfAgentDescription.setName(getAID());

    ServiceDescription serviceDescription = new ServiceDescription();

    serviceDescription.setType("OPENBRAVO-AUCTION");
    serviceDescription.setName(this.getLocalName());
    dfAgentDescription.addServices(serviceDescription);

    try {
      DFService.register(this, dfAgentDescription);
    } catch (FIPAException fe) {
      fe.printStackTrace();
    }

    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      auction = (Auction) args[0];
    }

    try {
      if (auction instanceof EnglishAuction) {
      } else if (auction instanceof EnglishAuction) {
        auctioneerAgentController = getContainerController().createNewAgent("ENGLISH-AUCTIONEER",
            "org.openbravo.auction.agents.EnglishAuctioneerAgent", new Object[0]);
      } else if (auction instanceof DutchAuction) {
        auctioneerAgentController = getContainerController().createNewAgent("DUTCH-AUCTIONEER",
            "org.openbravo.auction.agents.DutchAuctioneerAgent", new Object[0]);
      } else if (auction instanceof JapaneseAuction) {
        auctioneerAgentController = getContainerController().createNewAgent("JAPANESE-AUCTIONEER",
            "org.openbravo.auction.agents.JapaneseAuctioneerAgent", new Object[0]);
      }

      // auctioneerAgentController.start(); // TODO: LO MANDAREMOS A EMPEZAR CUANDO SE HAYA
      // TERMINADO
      // EL TIEMPO DE SUSCRIPCIÓN Y LA SUBASTA SE DE POR EMPEZADA.
    } catch (StaleProxyException e) {
      e.printStackTrace();
    }

    addBehaviour(new NewBuyerSubscriptionBehaviour(auction.getCelebrationDate()));

    ArrayList<String> newAuctionNotificationMessageElements = new ArrayList<String>();
    newAuctionNotificationMessageElements.add(auction.toString());

    addBehaviour(new startOpenbravoAuctionRestServerBehaviour());
    addBehaviour(new NewAuctionNotificationBehaviour(newAuctionNotificationMessageElements));
  }

  @Override
  protected void takeDown() {
    // Deregister from the yellow pages.
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

}
