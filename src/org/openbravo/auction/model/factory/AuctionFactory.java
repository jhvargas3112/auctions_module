package org.openbravo.auction.model.factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.Item;
import org.openbravo.auction.model.JapaneseAuction;

public class AuctionFactory {

  public static Auction getAuction(JSONObject jsonAuctionParameters) {

    String auctionType = null;

    try {
      auctionType = (String) jsonAuctionParameters.get("auctionType");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    HashMap<String, Object> englishAuctionParameters = JSONAuctionParametersToHashMap(
        jsonAuctionParameters);

    Date celebrationDate = (Date) englishAuctionParameters.get("celebrationDate");
    Date deadLine = (Date) englishAuctionParameters.get("deadLine");
    Integer maximumBiddersNum = (Integer) englishAuctionParameters.get("maximumBiddersNum");
    Item item = (Item) englishAuctionParameters.get("item");
    Double startingPrice = (Double) englishAuctionParameters.get("startingPrice");
    Double minimumSalePrice = (Double) englishAuctionParameters.get("minimumSalePrice");
    String additionalInformation = (String) englishAuctionParameters.get("additionalInformation");

    Auction auction = null;

    switch (auctionType) {
      case "Inglesa":
        auction = new EnglishAuction(celebrationDate, deadLine, maximumBiddersNum, item,
            startingPrice, minimumSalePrice, additionalInformation);
        break;
      case "Holandesa":
        auction = new DutchAuction(celebrationDate, deadLine, maximumBiddersNum, item,
            startingPrice, minimumSalePrice, additionalInformation);
        break;
      case "Japonesa":
        auction = new JapaneseAuction(celebrationDate, maximumBiddersNum, item, startingPrice,
            minimumSalePrice, additionalInformation);
        break;
    }

    return auction;
  }

  private static HashMap<String, Object> JSONAuctionParametersToHashMap(
      JSONObject jsonAuctionParameters) {
    String auctionType = null;
    Date celebrationDate = null;
    Date deadLine = null;
    Integer maximumBiddersNum = null;
    Item item = null;
    Double startingPrice = null;
    Double minimumSalePrice = null;
    String additionalInformation = "";

    try {
      auctionType = (String) jsonAuctionParameters.get("auctionType");

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      celebrationDate = dateFormat.parse((String) jsonAuctionParameters.get("celebrationDate") + " "
          + (String) jsonAuctionParameters.get("celebrationTime"));

      deadLine = dateFormat.parse((String) jsonAuctionParameters.get("deadLine") + " "
          + (String) jsonAuctionParameters.get("closingTime"));

      maximumBiddersNum = (Integer) jsonAuctionParameters.get("maximumBiddersNum");

      // FIXME: Tener en cuenta los valores nulos, sobre todo en volume y weight (Y EN TODOS EN
      // REALIDAD) y ponerle 0.0, "", o lo que corresponda por
      // defecto, por ejemplo, o mejor ponerlo a null. ESTO TAMBIÉN DEBERÍA VALIDARSE EN LA VISTA,
      // POR SUPUESTO.

      item = new Item((String) ((JSONObject) jsonAuctionParameters.get("auctionItem")).get("name"),
          (String) ((JSONObject) jsonAuctionParameters.get("auctionItem")).get("description"),
          (String) ((JSONObject) jsonAuctionParameters.get("auctionItem")).get("category"),
          Double.parseDouble(
              ((JSONObject) jsonAuctionParameters.get("auctionItem")).get("volume").toString()),
          Double.parseDouble(
              ((JSONObject) jsonAuctionParameters.get("auctionItem")).get("weight").toString()));

      startingPrice = Double.parseDouble(jsonAuctionParameters.get("startingPrice").toString());

      minimumSalePrice = Double
          .parseDouble(jsonAuctionParameters.get("minimumSalePrice").toString());

      if (!jsonAuctionParameters.isNull("additionalInformation")) {
        additionalInformation = (String) jsonAuctionParameters.get("additionalInformation");
      }

    } catch (JSONException e1) {
      e1.printStackTrace();
    } catch (ParseException e2) {
      e2.printStackTrace();
    }

    HashMap<String, Object> auctionParameters = new HashMap<String, Object>();

    auctionParameters.put("celebrationDate", celebrationDate);
    auctionParameters.put("maximumBiddersNum", maximumBiddersNum);
    auctionParameters.put("item", item);
    auctionParameters.put("startingPrice", startingPrice);
    auctionParameters.put("minimumSalePrice", minimumSalePrice);
    auctionParameters.put("additionalInformation", additionalInformation);

    if (StringUtils.isNotBlank(auctionType) && !StringUtils.equals(auctionType, "Japonesa")) {
      auctionParameters.put("deadLine", deadLine);
    }

    return auctionParameters;
  }

}
