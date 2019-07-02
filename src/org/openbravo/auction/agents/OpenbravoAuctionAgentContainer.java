package org.openbravo.auction.agents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.PlatformController;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public enum OpenbravoAuctionAgentContainer {
  INSTANCE;

  private PlatformController mainContainer = null;

  public PlatformController getValue() {
    if (mainContainer == null) {
      Runtime runtime = jade.core.Runtime.instance();
      runtime.setCloseVM(true);

      Profile profile = new ProfileImpl(null, 1200, null);

      mainContainer = runtime.createMainContainer(profile);

      return mainContainer;
    } else {
      return mainContainer;
    }
  }

  public void setValue(PlatformController mainContainer) {
    this.mainContainer = mainContainer;
  }

}
