package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.service.impl.DutchAuctionServiceImpl;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AcceptCurrentItemPrice extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get
  public Representation acceptCurrentItemPrice() {
    String auctionId = getQueryValue("auction_id");
    String buyerId = getQueryValue("buyer_id");

    Representation auctionCelebrationFtl = null;

    Auction auction = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        auction = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);
        if (auction.getAuctionState().getAuctionStateEnum() == AuctionStateEnum.IT_IS_CELEBRATING) {
          new DutchAuctionServiceImpl().finishAuctionCelebration(auctionId, buyerId);

          auctionCelebrationFtl = new ClientResource(
              LocalReference.createClapReference(getClass().getPackage())
                  + "/templates/auction_winner_page.ftl").get();
        } else {
          auctionCelebrationFtl = new ClientResource(
              LocalReference.createClapReference(getClass().getPackage())
                  + "/templates/dutch_auction_celebration_finished.ftl").get();
        }
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

    return new TemplateRepresentation(auctionCelebrationFtl, null, MediaType.TEXT_HTML);
  }
}
