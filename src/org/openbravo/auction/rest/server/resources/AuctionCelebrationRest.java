package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;
import java.util.Map;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AuctionCelebrationRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get
  public Representation getAuctionCelebrationPage() {
    Integer auctionCode = Integer.parseInt(getQueryValue("auction_id"));

    Representation auctionCelebrationFtl = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionCode)) {
        Auction auction = ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionCode);

        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("auction", auction);

        auctionCelebrationFtl = new ClientResource(
            LocalReference.createClapReference(getClass().getPackage())
                + "/templates/auction_celebration.ftl").get();

        return new TemplateRepresentation(auctionCelebrationFtl, dataModel, MediaType.TEXT_HTML);
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }

    return auctionCelebrationFtl;
  }
}
