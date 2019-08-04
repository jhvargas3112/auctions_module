package org.openbravo.auction.rest.server.resources;

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

public class RequestToJoinToTheAuction extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get
  public Representation requestToJoinToTheAuction() {
    String auctionId = getQueryValue("auction_id");

    Representation auctionCelebrationFtl = null;

    Map<String, Object> dataModel = new HashMap<String, Object>();

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        dataModel = new HashMap<String, Object>();
        dataModel.put("auction_id", auctionId);

        auctionCelebrationFtl = new ClientResource(
            LocalReference.createClapReference(getClass().getPackage())
                + "/templates/auction_celebration_auth.ftl").get();
      } else {
        auctionCelebrationFtl = new ClientResource(
            LocalReference.createClapReference(getClass().getPackage())
                + "/templates/auction_not_exist.ftl").get();
      }
    } else {
      auctionCelebrationFtl = new ClientResource(
          LocalReference.createClapReference(getClass().getPackage())
              + "/templates/auction_not_exist.ftl").get();
    }

    return new TemplateRepresentation(auctionCelebrationFtl, dataModel, MediaType.TEXT_HTML);
  }
}
