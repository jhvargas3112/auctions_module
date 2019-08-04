package org.openbravo.auction.rest.server.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AuctionCelebrationAuthentication extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get
  public Representation getAuctionCelebrationPage() {
    String auctionId = getQueryValue("auction_id");
    String buyerId = getQueryValue("buyer_id");

    Representation auctionCelebrationFtl = null;

    Map<String, Object> dataModel = new HashMap<String, Object>();

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        ArrayList<String> auctionBuyersIds = ((HashMap<String, ArrayList<String>>) getContext()
            .getAttributes()
            .get("auction_buyerIds")).get(auctionId);

        Auction auction = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);
        if (auctionBuyersIds.contains(buyerId)) {
          if (auction.getAuctionState()
              .getAuctionStateEnum() == AuctionStateEnum.IT_IS_CELEBRATING) {
            dataModel.put("auction", auction);
            dataModel.put("buyer_id", buyerId);

            switch (auction.getAuctionType().getAuctionTypeEnum()) {
              case ENGLISH:
                auctionCelebrationFtl = new ClientResource(
                    LocalReference.createClapReference(getClass().getPackage())
                        + "/templates/english_auction_celebration.ftl").get();
                break;
              case DUTCH:
                auctionCelebrationFtl = new ClientResource(
                    LocalReference.createClapReference(getClass().getPackage())
                        + "/templates/dutch_auction_celebration.ftl").get();
                break;
              case JAPANESE:
                auctionCelebrationFtl = new ClientResource(
                    LocalReference.createClapReference(getClass().getPackage())
                        + "/templates/japanese_auction_celebration.ftl").get();
                break;
            }
          } else {
            dataModel.put("auction_id", auctionId);
            dataModel.put("celebration_date", auction.getCelebrationDate());

            auctionCelebrationFtl = new ClientResource(
                LocalReference.createClapReference(getClass().getPackage())
                    + "/templates/auction_celebration_is_not_available.ftl").get();
          }
        } else {
          dataModel.put("auction_id", auctionId);

          auctionCelebrationFtl = new ClientResource(
              LocalReference.createClapReference(getClass().getPackage())
                  + "/templates/auction_celebration_auth_failed.ftl").get();
        }
      }
    }

    return new TemplateRepresentation(auctionCelebrationFtl, dataModel, MediaType.TEXT_HTML);
  }
}
