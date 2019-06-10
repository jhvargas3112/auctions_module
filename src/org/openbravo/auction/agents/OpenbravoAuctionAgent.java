package org.openbravo.auction.agents;

import java.util.HashMap;

import org.openbravo.auction.agents.behaviours.NewBuyerSubscriptionBehaviour;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.utils.AuctionState;
import org.springframework.beans.factory.annotation.Autowired;

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

  @Autowired
  private OpenbravoAuctionService openbravoAuctionService;

  private Auction auction;
  private AgentController auctioneerAgentController;
  private HashMap<String, BuyerAgent> buyers; // FIXME: VALORAR QUÉ USAR COMO CLAVE Y QUÉ COMO
                                              // VALOR. CREO QUE BuyerAgent DEBERÍA SER UN TIPO
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
      auctioneerAgentController = getContainerController().createNewAgent("AUCTIONEER",
          "org.openbravo.auction.Agents.OpenbravoAuctionAgent", new Object[0]);
      // auctioneerAgentController.start(); TODO: LO MANDAREMOS A EMPEZAR CUANDO SE HAYA TERMINADO
      // EL TIEMPO DE SUSCRIPCIÓN Y LA SUBASTA SE DE EMPEZADA.
    } catch (StaleProxyException e) {
      e.printStackTrace();
    }

    addBehaviour(new NewBuyerSubscriptionBehaviour(auction.getCelebrationDate()));

  }

  @Override
  protected void takeDown() {
    // Deregister from the yellow pages
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
