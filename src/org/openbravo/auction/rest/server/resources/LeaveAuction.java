package org.openbravo.auction.rest.server.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.JapaneseAuctionBuyer;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class LeaveAuction extends ServerResource {

  @SuppressWarnings("unchecked")
  @Get
  public Representation continueIntheAuction() {
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

        AuctionStateEnum auctionState = auction.getAuctionState().getAuctionStateEnum();

        if (auctionState == AuctionStateEnum.IT_IS_CELEBRATING) {
          ((TreeSet<JapaneseAuctionBuyer>) auction.getAuctionBuyers())
              .removeIf(auctionBuyer -> StringUtils.equals(auctionBuyer.getId(), buyerId));

          HashMap<String, ArrayList<String>> auctionBuyersIds = (HashMap<String, ArrayList<String>>) ((ConcurrentHashMap<String, Object>) getContext()
              .getAttributes()).get("auction_buyerIds");

          ArrayList<String> buyerIds = auctionBuyersIds.get(auctionId);

          buyerIds.remove(buyerId);

          getContext().getAttributes().put("auction_buyerIds", auctionBuyersIds);

          auctionCelebrationFtl = new ClientResource(
              LocalReference.createClapReference(getClass().getPackage())
                  + "/templates/leave_japanese_auction.ftl").get();
        } else if (auctionState == AuctionStateEnum.FINISHED_WITH_WINNER) {
          auctionCelebrationFtl = new ClientResource(
              LocalReference.createClapReference(getClass().getPackage())
                  + "/templates/auction_winner_page.ftl").get();
        } else {
          auctionCelebrationFtl = new ClientResource(
              LocalReference.createClapReference(getClass().getPackage())
                  + "/templates/japanese_auction_celebration_finished.ftl").get();
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
