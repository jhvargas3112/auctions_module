package org.openbravo.auction.rest.server.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GetAuctions extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get("json")
  public String getAuctions() {
    HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
        .get("auctions");

    ArrayList<JSONObject> auctionsCodesStates = new ArrayList<JSONObject>();

    Iterator<Entry<String, Auction>> it = auctions.entrySet().iterator();

    while (it.hasNext()) {
      Map.Entry<String, Auction> pair = (Map.Entry<String, Auction>) it.next();

      try {
        auctionsCodesStates.add(new JSONObject().accumulate("auction_id", pair.getKey())
            .accumulate("auction_state", pair.getValue().getAuctionState().getAuctionStateName()));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    return auctionsCodesStates.toString();
  }
}
