package org.openbravo.auction.handler;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.base.exception.OBException;
import org.openbravo.client.kernel.BaseActionHandler;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.plm.Product;

public class CreatePurchaseOrderHandler extends BaseActionHandler {
  @Override
  protected JSONObject execute(Map<String, Object> requestParameters, String auctionParameters) {
    try {
      final JSONObject jsonAuctionParameters = new JSONObject(auctionParameters);

      String auctionId = (String) (jsonAuctionParameters.get("auction_id"));

      Auction auction = new OpenbravoAuctionServiceImpl().getAuction(auctionId);

      if (auction.getAuctionState()
          .getAuctionStateEnum() == AuctionStateEnum.FINISHED_WITH_WINNER) {
        OBContext.setAdminMode(false);

        final OBCriteria<Product> productList = OBDal.getInstance().createCriteria(Product.class);
        productList.add(Restrictions.eq(Product.PROPERTY_SALE, auction.getItem().getName()));

        List<Product> products = productList.list();

        Product auctionItem = products.get(0);
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
