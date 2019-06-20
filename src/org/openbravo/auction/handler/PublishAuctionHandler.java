package org.openbravo.auction.handler;

import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.base.exception.OBException;
import org.openbravo.client.kernel.BaseActionHandler;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class PublishAuctionHandler extends BaseActionHandler {

  private OpenbravoAuctionService openbravoAuctionService;

  @Override
  protected JSONObject execute(Map<String, Object> requestParameters, String auctionParameters) {
    try {
      final JSONObject jsonAuctionParameters = new JSONObject(auctionParameters);

      openbravoAuctionService = new OpenbravoAuctionServiceImpl();

      openbravoAuctionService.publishAuction(jsonAuctionParameters);

      JSONObject response = new JSONObject();
      response.put("status", "ok");

      return response;
    } catch (Exception e) {
      throw new OBException(e);
    }
  }

}
