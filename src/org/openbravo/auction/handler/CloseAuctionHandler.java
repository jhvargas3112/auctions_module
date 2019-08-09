package org.openbravo.auction.handler;

import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.base.exception.OBException;
import org.openbravo.client.kernel.BaseActionHandler;

public class CloseAuctionHandler extends BaseActionHandler {
  @Override
  protected JSONObject execute(Map<String, Object> requestParameters, String auctionParameters) {
    try {
      final JSONObject jsonAuctionParameters = new JSONObject(auctionParameters);

      String auctionId = (String) (jsonAuctionParameters.get("auction_id"));

      new OpenbravoAuctionServiceImpl().closeAuction(auctionId);

      JSONObject response = new JSONObject();
      response.put("status", "ok");

      return response;
    } catch (Exception e) {
      e.printStackTrace();
      throw new OBException(e);
    }
  }
}
