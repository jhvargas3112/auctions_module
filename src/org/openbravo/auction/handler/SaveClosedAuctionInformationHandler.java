package org.openbravo.auction.handler;

import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.base.exception.OBException;
import org.openbravo.client.kernel.BaseActionHandler;

public class SaveClosedAuctionInformationHandler extends BaseActionHandler {
  @Override
  protected JSONObject execute(Map<String, Object> requestParameters, String auctionParameters) {
    try {
      final JSONObject jsonAuctionParameters = new JSONObject(auctionParameters);

      new OpenbravoAuctionServiceImpl().publishAuction(jsonAuctionParameters);

      JSONObject response = new JSONObject();
      response.put("status", "ok");

      return response;
    } catch (Exception e) {
      e.printStackTrace();
      throw new OBException(e);
    }
  }
}
