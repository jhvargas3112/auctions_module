package org.openbravo.auction.agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class OpenbravoAgent extends Agent {

  @Override
  protected void setup() {

  }

  protected void registerAgent(String agentType, String name) {
    System.out.println("Registrando agente " + name + " en páginas amarillas");

    DFAgentDescription dfAgentDescription = new DFAgentDescription();
    dfAgentDescription.setName(getAID());
    ServiceDescription serviceDescription = new ServiceDescription();
    serviceDescription.setType(agentType);
    serviceDescription.setName(name);
    dfAgentDescription.addServices(serviceDescription);

    try {
      DFService.register(this, dfAgentDescription);
    } catch (FIPAException e) {
      e.printStackTrace();
    }
  }

  public DFAgentDescription[] searchAgent(String agentType) {
    DFAgentDescription dfAgentDescription = new DFAgentDescription();
    ServiceDescription serviceDescription = new ServiceDescription();
    serviceDescription.setType(agentType);
    dfAgentDescription.addServices(serviceDescription);

    DFAgentDescription[] results = null;

    try {
      results = DFService.search(this, dfAgentDescription);
    } catch (FIPAException fe) {
      fe.printStackTrace();
    }
    return results;
  }

}
