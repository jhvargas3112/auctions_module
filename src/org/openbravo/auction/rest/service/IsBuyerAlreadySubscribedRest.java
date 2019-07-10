package org.openbravo.auction.rest.service;

import java.util.TreeSet;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class IsBuyerAlreadySubscribedRest extends ServerResource {

  @SuppressWarnings("unchecked")
  @Get
  public Boolean isAuctionAlreadySubscribedRest() {
    return ((TreeSet<String>) getContext().getAttributes().get("subscribed_buyers"))
        .contains(getQueryValue("buyer_email"));
  }

}
