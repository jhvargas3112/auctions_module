package org.openbravo.auction.handler;

import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.utils.XMLUtils;
import org.openbravo.base.exception.OBException;
import org.openbravo.client.kernel.BaseActionHandler;

public class RemoveAuctionWinnersHandler extends BaseActionHandler {
  @Override
  protected JSONObject execute(Map<String, Object> requestParameters, String auctionParameters) {
    try {
      final JSONObject jsonAuctionParameters = new JSONObject(auctionParameters);

      JSONArray winnersToRemove = ((JSONArray) jsonAuctionParameters.get("winners_to_remove"));

      for (int i = 0; i < winnersToRemove.length(); ++i) {
        JSONObject buyer = new JSONObject(winnersToRemove.getString(i));

        String auctionId = buyer.get("auction_id").toString();
        String buyerEmail = buyer.get("email").toString();

        new XMLUtils().removeAuctionWinner(auctionId, buyerEmail);
      }

      JSONObject response = new JSONObject();
      response.put("status", "ok");

      return response;
    } catch (Exception e) {
      e.printStackTrace();
      throw new OBException(e);
    }
  }
}
