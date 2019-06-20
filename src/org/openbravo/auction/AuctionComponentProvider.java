package org.openbravo.auction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.openbravo.client.kernel.BaseComponentProvider;
import org.openbravo.client.kernel.Component;

/**
 * @author Jhonny Vargas.
 */

public class AuctionComponentProvider extends BaseComponentProvider {

  @Override
  public Component getComponent(String componentId, Map<String, Object> parameters) {
    return null;
  }

  @Override
  public List<ComponentResource> getGlobalComponentResources() {
    final List<ComponentResource> globalResources = new ArrayList<ComponentResource>();

    globalResources.add(
        createStaticResource("web/org.openbravo.auction/js/admin-auction-control-view.js", false));
    globalResources
        .add(createStaticResource("web/org.openbravo.auction/js/new-auction-form-view.js", false));
    globalResources
        .add(createStaticResource("web/org.openbravo.auction/js/new-auction-items-view.js", false));
    globalResources
        .add(createStaticResource("web/org.openbravo.auction/js/new-auction-view.js", false));

    return globalResources;
  }

  @Override
  public List<String> getTestResources() {
    return Collections.emptyList();
  }

}
