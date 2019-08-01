package org.openbravo.auction.rest.server.resources;

import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.AuctionBuyer;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GetAuctionBuyersRest extends ServerResource {
  @Get("json")
  public String registeredBuyers() {
    Integer auctionCode = Integer.parseInt(getQueryValue("auction_id"));
    Auction auction = new OpenbravoAuctionServiceImpl().getAuction(auctionCode);

    ArrayList<JSONObject> auctionBuyers = new ArrayList<JSONObject>();

    Iterator<?> it = null;

    switch (auction.getAuctionType().getAuctionTypeEnum()) {
      case ENGLISH:
        it = ((EnglishAuction) auction).getAuctionBuyers().iterator();
        break;
      case DUTCH:
        it = ((DutchAuction) auction).getAuctionBuyers().iterator();
        break;
      case JAPANESE:
        it = ((JapaneseAuction) auction).getAuctionBuyers().iterator();
        break;
    }

    while (it.hasNext()) {
      try {
        AuctionBuyer auctionBuyer = (AuctionBuyer) it.next();

        JSONObject auctionBuerJSONObject = new JSONObject();
        auctionBuerJSONObject.accumulate("email", auctionBuyer.getEmail());

        if (auctionBuyer instanceof EnglishAuctionBuyer) {
          auctionBuerJSONObject.accumulate("last_offer",
              ((EnglishAuctionBuyer) auctionBuyer).getLastOffer());
        }

        auctionBuyers.add(auctionBuerJSONObject);

      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    return auctionBuyers.toString();
  }
}
