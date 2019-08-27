package org.openbravo.auction.model.factory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.DutchAuctionBuyer;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.model.Item;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.model.JapaneseAuctionBuyer;
import org.openbravo.auction.utils.AuctionState;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.AuctionType;
import org.openbravo.auction.utils.AuctionTypeEnum;

public class AuctionFactory {
  @SuppressWarnings("unchecked")
  public static Auction getAuction(JSONObject jsonAuctionParameters) {
    AuctionType auctionType = null;

    try {
      auctionType = new AuctionType(AuctionTypeEnum.valueOf(
          (String) ((JSONObject) jsonAuctionParameters.get("auctionType")).get("auctionTypeEnum")));
    } catch (JSONException e) {
      e.printStackTrace();
    }

    HashMap<String, Object> auctionParameters = JSONAuctionParametersToHashMap(
        jsonAuctionParameters);

    AuctionState auctionState = (AuctionState) auctionParameters.get("auctionState");
    Date celebrationDate = (Date) auctionParameters.get("celebrationDate");
    Date deadLine = (Date) auctionParameters.get("deadLine");
    Integer maximumBiddersNum = (Integer) auctionParameters.get("maximumBiddersNum");
    Item item = (Item) auctionParameters.get("item");
    BigDecimal startingPrice = (BigDecimal) auctionParameters.get("startingPrice");
    BigDecimal currentPrice = (BigDecimal) auctionParameters.get("currentPrice");
    BigDecimal minimumSalePrice = (BigDecimal) auctionParameters.get("minimumSalePrice");
    BigDecimal maximumSalePrice = (BigDecimal) auctionParameters.get("maximumSalePrice");
    Integer numberOfRounds = null;
    String additionalInformation = (String) auctionParameters.get("additionalInformation");
    TreeSet<?> auctionBuyers = (TreeSet<?>) auctionParameters.get("auctionBuyers");

    if (auctionType != null) {
      if (auctionType.getAuctionTypeEnum().equals(AuctionTypeEnum.DUTCH)
          || auctionType.getAuctionTypeEnum().equals(AuctionTypeEnum.JAPANESE)) {
        numberOfRounds = (Integer) auctionParameters.get("numberOfRounds");
      }
    }

    Auction auction = null;

    switch (auctionType.getAuctionTypeEnum()) {
      case ENGLISH:
        auction = new EnglishAuction(auctionState, celebrationDate, deadLine, maximumBiddersNum,
            item, startingPrice, currentPrice, additionalInformation,
            (TreeSet<EnglishAuctionBuyer>) auctionBuyers);
        break;
      case DUTCH:
        auction = new DutchAuction(auctionState, celebrationDate, deadLine, numberOfRounds,
            maximumBiddersNum, item, startingPrice, currentPrice, minimumSalePrice,
            additionalInformation, (TreeSet<DutchAuctionBuyer>) auctionBuyers);
        break;
      case JAPANESE:
        auction = new JapaneseAuction(auctionState, celebrationDate, deadLine, numberOfRounds,
            maximumBiddersNum, item, startingPrice, currentPrice, maximumSalePrice,
            additionalInformation, (TreeSet<JapaneseAuctionBuyer>) auctionBuyers);
        break;
    }

    return auction;
  }

  private static HashMap<String, Object> JSONAuctionParametersToHashMap(
      JSONObject jsonAuctionParameters) {
    AuctionType auctionType = null;
    AuctionState auctionState = null;
    Date celebrationDate = null;
    Date deadLine = null;
    Integer maximumBiddersNum = null;
    Item item = null;
    BigDecimal startingPrice = null;
    BigDecimal minimumSalePrice = null;
    BigDecimal maximumSalePrice = null;
    Integer numberOfRounds = null;
    BigDecimal currentPrice = null;
    String additionalInformation = "";
    TreeSet<EnglishAuctionBuyer> englishAuctionBuyers = new TreeSet<EnglishAuctionBuyer>();
    TreeSet<DutchAuctionBuyer> dutchAuctionBuyers = new TreeSet<DutchAuctionBuyer>();
    TreeSet<JapaneseAuctionBuyer> japaneseAuctionBuyers = new TreeSet<JapaneseAuctionBuyer>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    try {

      auctionType = new AuctionType(AuctionTypeEnum.valueOf(
          (String) ((JSONObject) jsonAuctionParameters.get("auctionType")).get("auctionTypeEnum")));

      auctionState = new AuctionState(
          AuctionStateEnum.valueOf((String) ((JSONObject) jsonAuctionParameters.get("auctionState"))
              .get("auctionStateEnum")));

      celebrationDate = dateFormat
          .parse(dateFormat.format(new Date((Long) jsonAuctionParameters.get("celebrationDate"))));

      deadLine = dateFormat
          .parse(dateFormat.format(new Date((Long) jsonAuctionParameters.get("deadLine"))));

      maximumBiddersNum = (Integer) jsonAuctionParameters.get("maximumBiddersNum");

      item = new Item((String) ((JSONObject) jsonAuctionParameters.get("item")).get("name"),
          (String) ((JSONObject) jsonAuctionParameters.get("item")).get("description"),
          (String) ((JSONObject) jsonAuctionParameters.get("item")).get("category"),
          Double.parseDouble(
              ((JSONObject) jsonAuctionParameters.get("item")).get("volume").toString()),
          Double.parseDouble(
              ((JSONObject) jsonAuctionParameters.get("item")).get("weight").toString()));

      startingPrice = new BigDecimal(
          Double.parseDouble(jsonAuctionParameters.get("startingPrice").toString()));

      currentPrice = new BigDecimal(
          Double.parseDouble(jsonAuctionParameters.get("currentPrice").toString()));

      if (!jsonAuctionParameters.isNull("additionalInformation")) {
        additionalInformation = (String) jsonAuctionParameters.get("additionalInformation");
      }

      JSONArray buyers = ((JSONArray) jsonAuctionParameters.get("auctionBuyers"));

      for (int i = 0; i < buyers.length(); ++i) {
        JSONObject buyer = new JSONObject(buyers.getString(i));

        String id = buyer.get("id").toString();
        String email = buyer.get("email").toString();

        switch (auctionType.getAuctionTypeEnum()) {
          case ENGLISH:
            englishAuctionBuyers.add(new EnglishAuctionBuyer(id, email,
                new BigDecimal(Double.parseDouble(buyer.get("lastOffer").toString()))));
            break;
          case DUTCH:
            dutchAuctionBuyers.add(new DutchAuctionBuyer(id, email));
            break;
          case JAPANESE:
            japaneseAuctionBuyers.add(new JapaneseAuctionBuyer(id, email));
            break;
        }
      }
    } catch (JSONException e1) {
      e1.printStackTrace();
    } catch (ParseException e2) {
      e2.printStackTrace();
    }

    HashMap<String, Object> auctionParameters = new HashMap<String, Object>();

    auctionParameters.put("auctionState", auctionState);
    auctionParameters.put("celebrationDate", celebrationDate);
    auctionParameters.put("deadLine", deadLine);
    auctionParameters.put("maximumBiddersNum", maximumBiddersNum);
    auctionParameters.put("item", item);
    auctionParameters.put("startingPrice", startingPrice);
    auctionParameters.put("currentPrice", currentPrice);
    auctionParameters.put("minimumSalePrice", minimumSalePrice);
    auctionParameters.put("maximumSalePrice", minimumSalePrice);
    auctionParameters.put("additionalInformation", additionalInformation);

    switch (auctionType.getAuctionTypeEnum()) {
      case ENGLISH:
        auctionParameters.put("auctionBuyers", englishAuctionBuyers);
        break;
      case DUTCH:
        auctionParameters.put("auctionBuyers", dutchAuctionBuyers);
        break;
      case JAPANESE:
        auctionParameters.put("auctionBuyers", japaneseAuctionBuyers);
        break;
    }

    if (auctionType != null) {
      if (auctionType.getAuctionTypeEnum().equals(AuctionTypeEnum.DUTCH)
          || auctionType.getAuctionTypeEnum().equals(AuctionTypeEnum.JAPANESE)) {
        try {

          numberOfRounds = Integer.parseInt(jsonAuctionParameters.get("numberOfRounds").toString());

          auctionParameters.put("numberOfRounds", numberOfRounds);

          if (auctionType.getAuctionTypeEnum().equals(AuctionTypeEnum.DUTCH)) {
            minimumSalePrice = new BigDecimal(
                Double.parseDouble(jsonAuctionParameters.get("minimumSalePrice").toString()));
            auctionParameters.put("minimumSalePrice", minimumSalePrice);
          }

          if (auctionType.getAuctionTypeEnum().equals(AuctionTypeEnum.JAPANESE)) {
            maximumSalePrice = new BigDecimal(
                Double.parseDouble(jsonAuctionParameters.get("maximumSalePrice").toString()));
            auctionParameters.put("maximumSalePrice", maximumSalePrice);
          }

        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }

    return auctionParameters;
  }
}
