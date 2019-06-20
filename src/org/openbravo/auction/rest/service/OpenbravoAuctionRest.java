package org.openbravo.auction.rest.service;

import org.apache.commons.lang3.StringUtils;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.restlet.data.Status;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class OpenbravoAuctionRest extends ServerResource {
  private OpenbravoAuctionService openbravoAuctionService = new OpenbravoAuctionServiceImpl();

  @Override
  public Representation handle() {
    String buyerEmail = getQueryValue("buyer_email");
    String requestPath = getRequest().getOriginalRef().getPath();

    if (StringUtils.equals(requestPath, "/openbravo/auction/join_to")) {
      joinToAuction(buyerEmail);
    } else {
      auctionLeave();
    }

    return new EmptyRepresentation();
  }

  @Post
  public void joinToAuction(String buyerEmail) {
    openbravoAuctionService.registerBuyerToAuction(buyerEmail);

    getResponse().setStatus(new Status(200));
  }

  @Post
  public void auctionLeave() {
    getResponse().setStatus(new Status(200));
  }

}
