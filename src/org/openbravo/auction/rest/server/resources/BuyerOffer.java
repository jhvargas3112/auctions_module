package org.openbravo.auction.rest.server.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.AuctionBuyer;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class BuyerOffer extends ServerResource {
  @SuppressWarnings("unchecked")
  @Post("json")
  public String setNewBuyerOffer() {
    Auction auction = null;

    String auctionId = getQueryValue("auction_id");
    String buyerId = getQueryValue("buyer_id");
    BigDecimal buyerOffer = new BigDecimal(Double.parseDouble(getQueryValue("buyer_offer")));

    Boolean resp = false;

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        ArrayList<String> auctionBuyersIds = ((HashMap<String, ArrayList<String>>) getContext()
            .getAttributes()
            .get("auction_buyerIds")).get(auctionId);

        auction = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);
        if (auctionBuyersIds.contains(buyerId)) {
          if (auction.getAuctionState()
              .getAuctionStateEnum() == AuctionStateEnum.IT_IS_CELEBRATING) {
            // Sets the new buyer offer.
            String buyerEmail = getBuyerEmail(auction.getAuctionBuyers(), buyerId);

            if (buyerOffer
                .compareTo(((TreeSet<EnglishAuctionBuyer>) auction.getAuctionBuyers()).first()
                    .getLastOffer()) == 1
                && buyerOffer.compareTo(auction.getStartingPrice()) == 1) {
              ((TreeSet<EnglishAuctionBuyer>) auction.getAuctionBuyers())
                  .removeIf(auctionBuyer -> StringUtils.equals(auctionBuyer.getId(), buyerId));
              ((TreeSet<EnglishAuctionBuyer>) auction.getAuctionBuyers())
                  .add(new EnglishAuctionBuyer(buyerId, buyerEmail, buyerOffer));

              resp = true;
            }
          }
        }
      }
    }

    JSONObject JSONObject = new JSONObject();
    try {
      JSONObject.accumulate("status", resp)
          .accumulate("highest_offer",
              ((TreeSet<EnglishAuctionBuyer>) auction.getAuctionBuyers()).first().getLastOffer());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return JSONObject.toString();
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
