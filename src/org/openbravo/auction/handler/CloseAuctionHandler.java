package org.openbravo.auction.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.client.kernel.BaseActionHandler;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.businesspartner.Category;
import org.openbravo.model.common.businesspartner.Location;
import org.openbravo.model.common.currency.Currency;
import org.openbravo.model.common.enterprise.DocumentType;
import org.openbravo.model.common.enterprise.Warehouse;
import org.openbravo.model.common.order.Order;
import org.openbravo.model.common.order.OrderLine;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.financialmgmt.payment.PaymentTerm;
import org.openbravo.model.financialmgmt.tax.TaxRate;
import org.openbravo.model.pricing.pricelist.PriceList;

public class CloseAuctionHandler extends BaseActionHandler {
  @Override
  protected JSONObject execute(Map<String, Object> requestParameters, String auctionParameters) {
    try {
      final JSONObject jsonAuctionParameters = new JSONObject(auctionParameters);

      String auctionId = (String) (jsonAuctionParameters.get("auction_id"));

      Auction auction = new OpenbravoAuctionServiceImpl().getAuction(auctionId);

      new OpenbravoAuctionServiceImpl().closeAuction(auctionId);

      if (auction.getAuctionState()
          .getAuctionStateEnum() == AuctionStateEnum.FINISHED_WITH_WINNER) {

        OBContext.setAdminMode(false);

        final OBCriteria<Product> productList = OBDal.getInstance().createCriteria(Product.class);
        productList.add(Restrictions.eq(Product.PROPERTY_NAME, auction.getItem().getName()));

        Product auctionItem = productList.list().get(0);

        // ----------------------------------------------------------------------------

        // create business partner object
        final BusinessPartner auctionBuyer = OBProvider.getInstance().get(BusinessPartner.class);

        // set some values
        auctionBuyer.setSearchKey(auction.getWinnerEmail());
        auctionBuyer.setName(auction.getWinnerEmail());
        auctionBuyer.setName2(auction.getWinnerEmail());
        auctionBuyer.setDescription("venta por subasta");

        final OBCriteria<Category> categoryList = OBDal.getInstance()
            .createCriteria(Category.class);
        categoryList.add(Restrictions.eq(Product.PROPERTY_SEARCHKEY, "Customer - Tier 1"));

        Category buyerCategory = categoryList.list().get(0);

        auctionBuyer.setBusinessPartnerCategory(buyerCategory);

        OBDal.getInstance().save(auctionBuyer);

        // -----------------------------------------------------------------------------

        // create business partner location object
        final Location location = OBProvider.getInstance().get(Location.class);

        // set some values
        location.setName("");
        location.setBusinessPartner(auctionBuyer);

        OBDal.getInstance().save(location);

        // -----------------------------------------------------------------------------

        // create business order object
        final Order order = OBProvider.getInstance().get(Order.class);

        // set some values
        order.setBusinessPartner(auctionBuyer);
        order.setPartnerAddress(location);

        final OBCriteria<DocumentType> documentTypeList = OBDal.getInstance()
            .createCriteria(DocumentType.class);
        documentTypeList.add(Restrictions.eq(Product.PROPERTY_NAME, "Standard Order"));

        DocumentType documentType = documentTypeList.list().get(0);

        order.setDocumentType(documentType);
        order.setTransactionDocument(documentType);

        Date date = new Date();

        order.setOrderDate(date);
        order.setAccountingDate(date);
        order.setScheduledDeliveryDate(date);

        final OBCriteria<Currency> currencyList = OBDal.getInstance()
            .createCriteria(Currency.class);
        currencyList.add(Restrictions.eq(Currency.PROPERTY_ID, "102"));

        Currency currency = currencyList.list().get(0);

        order.setCurrency(currency);

        final OBCriteria<PaymentTerm> paymentTermList = OBDal.getInstance()
            .createCriteria(PaymentTerm.class);
        paymentTermList.add(Restrictions.eq(PaymentTerm.PROPERTY_NAME, "30 days"));

        order.setPaymentTerms(paymentTermList.list().get(0));

        final OBCriteria<Warehouse> warehouseTermList = OBDal.getInstance()
            .createCriteria(Warehouse.class);
        warehouseTermList.add(Restrictions.eq(Warehouse.PROPERTY_SEARCHKEY, "RN"));

        Warehouse warehouse = warehouseTermList.list().get(0);

        order.setWarehouse(warehouse);

        final OBCriteria<PriceList> priceList = OBDal.getInstance().createCriteria(PriceList.class);
        priceList.add(Restrictions.eq(PriceList.PROPERTY_NAME, "Tarifa de ventas"));

        order.setPriceList(priceList.list().get(0));

        // -----------------------------------------------------------------------------

        // create business order line object

        final OrderLine orderLine = OBProvider.getInstance().get(OrderLine.class);

        // set some values

        orderLine.setProduct(auctionItem);
        orderLine.setOrderedQuantity(new BigDecimal(1));
        orderLine.setUOM(auctionItem.getUOM());
        orderLine.setUnitPrice(auction.getCurrentPrice());
        orderLine.setLineNetAmount(auction.getCurrentPrice());

        orderLine.setDeliveredQuantity(new BigDecimal(0));
        orderLine.setLineNo(10L);

        orderLine.setOrderDate(date);

        orderLine.setWarehouse(warehouse);
        orderLine.setCurrency(currency);

        final OBCriteria<TaxRate> taxRateList = OBDal.getInstance().createCriteria(TaxRate.class);
        taxRateList.add(Restrictions.eq(TaxRate.PROPERTY_NAME, "Ventas exentas"));

        orderLine.setTax(taxRateList.list().get(0));

        orderLine.setBusinessPartner(auctionBuyer);
        orderLine.setPartnerAddress(location);

        orderLine.setSalesOrder(order);

        // -----------------------------------------------------------------------------

        ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
        orderLines.add(orderLine);

        order.setOrderLineList(orderLines);

        OBDal.getInstance().save(order);
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
