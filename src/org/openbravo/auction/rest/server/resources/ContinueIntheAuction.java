package org.openbravo.auction.rest.server.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.AuctionBuyer;
import org.openbravo.auction.service.impl.JapaneseAuctionServiceImpl;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ContinueIntheAuction extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get
  public Representation continueIntheAuction() {
    String auctionId = getQueryValue("auction_id");
    String buyerId = getQueryValue("buyer_id");

    Representation auctionCelebrationFtl = null;

    Map<String, Object> dataModel = new HashMap<String, Object>();

    Auction auction = null;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        auction = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);
        AuctionStateEnum auctionState = auction.getAuctionState().getAuctionStateEnum();
        if (auctionState == AuctionStateEnum.IT_IS_CELEBRATING) {
          boolean auctionHasBeenFinished = new JapaneseAuctionServiceImpl()
              .finishAuctionCelebration(auctionId, buyerId);
          if (auctionHasBeenFinished) {
            auctionCelebrationFtl = new ClientResource(
                LocalReference.createClapReference(getClass().getPackage())
                    + "/templates/auction_winner_page.ftl").get();
          } else {
            dataModel.put("auction", auction);
            dataModel.put("auction_id", auctionId);
            dataModel.put("buyer_id", buyerId);
            dataModel.put("buyer_email", getBuyerEmail(auction.getAuctionBuyers(), buyerId));

            auctionCelebrationFtl = new ClientResource(
                LocalReference.createClapReference(getClass().getPackage())
                    + "/templates/japanese_auction_celebration.ftl").get();
          }
        } else if (auctionState == AuctionStateEnum.FINISHED_WITH_WINNER
            || auctionState == AuctionStateEnum.FINISHED_WITHOUT_WINNER) {
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

    return new TemplateRepresentation(auctionCelebrationFtl, dataModel, MediaType.TEXT_HTML);
  }

  private String getBuyerEmail(TreeSet<?> auctionBuyers, String buyerId) {
    Iterator<?> it = auctionBuyers.iterator();

    String buyerEmail = null;

    while (it.hasNext()) {
      AuctionBuyer auctionBuyer = (AuctionBuyer) it.next();

      if (StringUtils.equals(auctionBuyer.getId(), buyerId)) {
        buyerEmail = auctionBuyer.getEmail();
      }
    }

    return buyerEmail;
  }
}
