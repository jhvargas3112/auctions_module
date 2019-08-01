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

public class GetAuctionsRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Get("json")
  public String getAuctions() {
    HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
        .get("auctions");

    ArrayList<JSONObject> auctionsCodesStates = new ArrayList<JSONObject>();

    Iterator<Entry<Integer, Auction>> it = auctions.entrySet().iterator();

    while (it.hasNext()) {
      Map.Entry<Integer, Auction> pair = (Map.Entry<Integer, Auction>) it.next();

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
