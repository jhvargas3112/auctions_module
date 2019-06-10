package org.openbravo.auction.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.springframework.stereotype.Service;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@Service
public class OpenbravoAuctionServiceImpl implements OpenbravoAuctionService {

  @Override
  public Auction createAuction(String auctionType, Date celebrationDate, Date deadLine,
      Integer maximumBiddersNum, Double startingPrice, Double minimumSalePrice, String description,
      String additionalInformation) {

    Auction auction = null;

    switch (auctionType) {
      case "Inglesa":
        auction = new EnglishAuction(null, celebrationDate, deadLine, maximumBiddersNum,
            startingPrice, minimumSalePrice, description, additionalInformation);
        break;
      case "Holandesa":
        auction = new DutchAuction(null, celebrationDate, deadLine, maximumBiddersNum,
            startingPrice, minimumSalePrice, description, additionalInformation);
        break;
      case "Japonesa":
        auction = new JapaneseAuction(null, celebrationDate, deadLine, maximumBiddersNum,
            startingPrice, minimumSalePrice, description, additionalInformation);
        break;

    }

    return auction;
  }

  /*
   * FIXME: este método recibirá los campos del formulario de la interfaz de usuario. Cambiar el
   * tipo de parámetro y ajustarlo a lo aquí comentado.
   */
  @Override
  public void publishAuction(JSONObject jsonAuctionParameters) {

    String auctionType = null;

    try {
      auctionType = (String) jsonAuctionParameters.get("auctionType");

      String celebrationDateStr = "29-Apr-2010,13:00:14 PM";
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      // Date date = formatter.parse(testDate);

      Date celebrationDate = DateUtils
          .parseDate((String) jsonAuctionParameters.get("celebrationDate"), new String[0]);
      Date deadLine = DateUtils.parseDate((String) jsonAuctionParameters.get("celebrationDate"),
          new String[0]);
      int maximumBiddersNum = Integer
          .parseInt((String) jsonAuctionParameters.get("celebrationDate"));
    } catch (JSONException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Auction auction = createAuction(auctionType, jsonAuctionParameters);
    Auction auction = null;

    Runtime runtime = jade.core.Runtime.instance();
    runtime.setCloseVM(true);

    // Create a default profile
    Profile profile = new ProfileImpl(null, 1200, null);

    ContainerController mainContainer = runtime.createMainContainer(profile);

    // now set the default Profile to start a container
    // ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
    // ContainerController cont = runtime.createAgentContainer(pContainer);

    AgentController openbravoAuctionAgent = null;

    Object[] openbravoAuctionAgentAurguments = new Object[3];
    openbravoAuctionAgentAurguments[0] = auction;

    try {
      openbravoAuctionAgent = mainContainer.createNewAgent("OPENBRAVO-AUCTION",
          "org.openbravo.auction.Agents.OpenbravoAuctionAgent", openbravoAuctionAgentAurguments);

      openbravoAuctionAgent.start();

    } catch (StaleProxyException e) {
      e.printStackTrace();
    }

  }

}
