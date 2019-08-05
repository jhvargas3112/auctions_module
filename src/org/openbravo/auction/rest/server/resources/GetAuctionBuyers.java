package org.openbravo.auction.rest.server.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.AuctionBuyer;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GetAuctionBuyers extends ServerResource {
  @Get("json")
  public String registeredBuyers() {
    String auctionId = getQueryValue("auction_id");

    TreeSet<AuctionBuyer> auctionBuyers = new OpenbravoAuctionServiceImpl()
        .getAuctionBuyers(auctionId);

    ArrayList<JSONObject> JSONAuctionBuyers = new ArrayList<JSONObject>();

    Iterator<?> it = auctionBuyers.iterator();

    while (it.hasNext()) {
      try {
        AuctionBuyer auctionBuyer = (AuctionBuyer) it.next();

        JSONObject auctionBuerJSONObject = new JSONObject();
        auctionBuerJSONObject.accumulate("email", auctionBuyer.getEmail());

        if (auctionBuyer instanceof EnglishAuctionBuyer) {
          auctionBuerJSONObject.accumulate("last_offer",
              ((EnglishAuctionBuyer) auctionBuyer).getLastOffer());
        }

        JSONAuctionBuyers.add(auctionBuerJSONObject);

      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    return JSONAuctionBuyers.toString();
  }
}
