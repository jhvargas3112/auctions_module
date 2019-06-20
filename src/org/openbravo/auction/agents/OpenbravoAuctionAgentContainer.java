package org.openbravo.auction.agents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public enum OpenbravoAuctionAgentContainer {
  INSTANCE;

  ContainerController mainContainer;

  public ContainerController getValue() {
    Runtime runtime = jade.core.Runtime.instance();
    runtime.setCloseVM(true);

    // Create a default profile
    Profile profile = new ProfileImpl(null, 1200, null);

    mainContainer = runtime.createMainContainer(profile);

    return mainContainer;
  }

  public void setValue(ContainerController mainContainer) {
    this.mainContainer = mainContainer;
  }

}
