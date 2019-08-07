package org.openbravo.auction.handler;

import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.client.kernel.BaseActionHandler;

public class StartOpenbravoAuctionRestServerHandler extends BaseActionHandler {
  @Override
  protected JSONObject execute(Map<String, Object> requestParameters, String auctionParameters) {
    new OpenbravoAuctionServiceImpl().startOpenbravoAuctionRestServer();

    return new JSONObject();
  }
}
