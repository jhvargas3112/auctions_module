package org.openbravo.auction.rest.service;

import java.util.HashMap;
import java.util.Map;

import org.openbravo.auction.model.Auction;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AuctionCelebrationRest extends ServerResource {
  @Get
  public Representation getAuctionCelebrationPage() {
    Auction auction = (Auction) getContext().getAttributes().get("auction");

    Map<String, Object> dataModel = new HashMap<String, Object>();
    dataModel.put("auction", auction);

    Representation auctionCelebrationFtl = new ClientResource(
        LocalReference.createClapReference(getClass().getPackage())
            + "/templates/auction_celebration.ftl").get();

    return new TemplateRepresentation(auctionCelebrationFtl, dataModel, MediaType.TEXT_HTML);
  }
}
