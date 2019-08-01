package org.openbravo.auction.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.concurrence.ReduceAuctionItemPrice;
import org.openbravo.auction.concurrence.StartAuctionCelebration;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.model.factory.AuctionFactory;
import org.openbravo.auction.rest.server.OpenbravoAuctionRestServer;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.AuctionTypeEnum;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class OpenbravoAuctionServiceImpl implements OpenbravoAuctionService {
  @Override
  public void publishAuction(JSONObject jsonAuctionParameters) {
    Auction auction = (Auction) AuctionFactory.getAuction(jsonAuctionParameters);

    if (!OpenbravoAuctionRestServer.INSTANCE.isStarted()) {
      OpenbravoAuctionRestServer.INSTANCE.start();
    }

    ClientResource clientResource = new ClientResource(
        "http://localhost:8111/openbravo/auction/publish");

    Representation JSONAuctionRepresentation = clientResource.put(auction);

    Integer auctionId = null;

    try {
      auctionId = Integer.parseInt(JSONAuctionRepresentation.getText().toString());
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }

    ArrayList<String> newAuctionNotificationMessageElements = new ArrayList<String>();
    newAuctionNotificationMessageElements.add(auction.toString());
    notifyBidders(newAuctionNotificationMessageElements, auctionId);

    new Thread(new StartAuctionCelebration(auctionId, auction.getCelebrationDate())).start();

    try {

      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

      Document document = documentBuilder.newDocument();

      Element root = document.createElement("Root");

      document.appendChild(root);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);

      ClassLoader classLoader = getClass().getClassLoader();
      Properties appProps = new Properties();
      try {
        String resource = classLoader.getResource("config.properties").getPath();
        appProps.load(new FileInputStream(resource));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      StreamResult streamResult = new StreamResult(
          appProps.getProperty("registered_buyers_path_file") + "/registered_buyers.xml");

      transformer.transform(domSource, streamResult);

    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (TransformerException tfe) {
      tfe.printStackTrace();
    }
  }

  @Override
  public void notifyBidders(ArrayList<String> newAuctionNotificationMessageElements,
      Integer auctionId) {
    ClassLoader classLoader = getClass().getClassLoader();
    Properties appProps = new Properties();
    try {
      String resource = classLoader.getResource("bidders").getPath();
      appProps.load(new FileInputStream(resource));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String[] receivers = appProps.getProperty("bidders").split(",");

    notifyBidders(receivers, newAuctionNotificationMessageElements, auctionId);
  }

  @Override
  public void notifyBidders(String[] receivers,
      ArrayList<String> newAuctionNotificationMessageElements, Integer auctionId) {
    sendEmailToBidders(receivers, newAuctionNotificationMessageElements, auctionId);
  }

  private final HashMap<String, Object> getSenderEmailParameters() {
    ClassLoader classLoader = getClass().getClassLoader();
    Properties appProps = new Properties();
    try {
      String resource = classLoader.getResource("config.properties").getPath();
      appProps.load(new FileInputStream(resource));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    HashMap<String, Object> emailSenderParameters = new HashMap<String, Object>();

    emailSenderParameters.put("emailFrom", appProps.getProperty("email"));
    emailSenderParameters.put("password", appProps.getProperty("password"));
    emailSenderParameters.put("hostname", appProps.getProperty("hostname"));
    emailSenderParameters.put("smtp_port", appProps.getProperty("smtp_port"));

    return emailSenderParameters;
  }

  private void sendEmailToBidders(String[] receivers,
      ArrayList<String> newAuctionNotificationMessageElements, Integer auctionId) {
    HashMap<String, Object> emailSenderParameters = getSenderEmailParameters();

    for (int i = 0; i < receivers.length; i++) {
      String receiverEmail = receivers[i];

      sendEmail((String) emailSenderParameters.get("emailFrom"), receiverEmail,
          (String) emailSenderParameters.get("password"),
          (String) emailSenderParameters.get("hostname"),
          Integer.parseInt((String) emailSenderParameters.get("smtp_port")), "Nueva subasta",
          createNewAuctionNotificationMessage(newAuctionNotificationMessageElements, auctionId,
              receiverEmail));
    }
  }

  private void sendEmail(String emailFrom, String emailTo, String password, String hostname,
      int smtp_port, String subject, String message) {
    try {
      Email sendingEmail = new SimpleEmail();
      sendingEmail.setHostName(hostname);
      sendingEmail.setSmtpPort(smtp_port);
      sendingEmail.setAuthenticator(new DefaultAuthenticator(emailFrom, password));
      sendingEmail.setSSLOnConnect(true);
      sendingEmail.setFrom(emailFrom);
      sendingEmail.setSubject(subject);
      sendingEmail.setMsg(message);
      sendingEmail.addTo(emailTo);
      sendingEmail.send();
    } catch (EmailException e1) {
      e1.printStackTrace();
    }
  }

  @Override
  public String createNewAuctionNotificationMessage(
      ArrayList<String> newAuctionNotificationMessageElements, Integer auctionId,
      String receiverEmail) {

    StringBuilder sb = new StringBuilder();

    for (String element : newAuctionNotificationMessageElements) {
      sb.append(element).append("\n");
    }

    sb.append("\n\n")
        .append("Apuntate a la subasta, haciendo click en el siguiente enlace: "
            + "http://localhost:8111/openbravo/auction/join?auction_id=" + auctionId
            + "&buyer_email=" + receiverEmail);

    return sb.toString();
  }

  @Override
  public String createNewSubscriptionNotificationMessage(Integer auctionId, String receiverEmail) {
    Auction auction = getAuction(auctionId);

    String newSubscriptionNotificationMessage = "Usted acaba de inscribirse en la subasta que se celebrá el día "
        + auction.getCelebrationDate() + "."
        + "\n\nPara poder empezar a participar en la subasta en la fecha indicada, deberá acceder a la URL "
        + "http://localhost:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + " e identificarse introduciendo el código 32323."
        + "\n\nA continuación, le recordamos la información de la subasta: \n\n"
        + auction.toString();

    return newSubscriptionNotificationMessage;
  }

  @Override
  public void notifySubscription(Integer auctionId, String buyerEmail) {
    HashMap<String, Object> emailSenderParameters = getSenderEmailParameters();

    sendEmail((String) emailSenderParameters.get("emailFrom"), buyerEmail,
        (String) emailSenderParameters.get("password"),
        (String) emailSenderParameters.get("hostname"),
        Integer.parseInt((String) emailSenderParameters.get("smtp_port")),
        "Confirmación de inscripción a subasta",
        createNewSubscriptionNotificationMessage(auctionId, buyerEmail));
  }

  @Override
  public void startAuctionCelebration(Integer auctionId) {
    changeAuctionState(auctionId, AuctionStateEnum.IT_IS_CELEBRATING);

    Auction auction = getAuction(auctionId);

    switch (auction.getAuctionType().getAuctionTypeEnum()) {

      case ENGLISH:
        break;
      case DUTCH:
        DutchAuction dutchAuction = (DutchAuction) auction;

        DutchAuctionServiceImpl dutchAuctionServiceImpl = new DutchAuctionServiceImpl();
        Long periodOfTimeToDecreasePrice = dutchAuctionServiceImpl.getPeriodOfTimeToDecreasePrice(
            dutchAuction.getCelebrationDate(), dutchAuction.getDeadLine(),
            dutchAuction.getNumberOfRounds());

        Double amountToDecreasePrice = dutchAuctionServiceImpl.getAmountToDecreasePrice(
            dutchAuction.getStartingPrice(), dutchAuction.getMinimumSalePrice(),
            dutchAuction.getNumberOfRounds());

        new Thread(new ReduceAuctionItemPrice(dutchAuction.getStartingPrice(),
            dutchAuction.getMinimumSalePrice(), dutchAuction.getNumberOfRounds(),
            periodOfTimeToDecreasePrice, amountToDecreasePrice)).start();
        break;
      case JAPANESE:
        break;
    }
  }

  @Override
  public Auction getAuction(Integer auctionId) {
    ClientResource clientResource = new ClientResource(
        "http://localhost:8111/openbravo/auction/auction_info");
    clientResource.addQueryParameter("auction_id", auctionId.toString());

    Representation JSONAuctionRepresentation = clientResource.get();

    return getAuction(JSONAuctionRepresentation);
  }

  public Auction getAuction(Representation JSONAuctionRepresentation) {
    AuctionTypeEnum auctionType = null;
    JSONObject auctionInJSONFormat = null;

    try {
      auctionInJSONFormat = new JSONObject(JSONAuctionRepresentation.getText());

      auctionType = AuctionTypeEnum.valueOf(
          (String) ((JSONObject) auctionInJSONFormat.get("auctionType")).get("auctionTypeEnum"));
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }

    GsonBuilder builder = new GsonBuilder();

    builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
      @Override
      public Date deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
          JsonDeserializationContext context) throws JsonParseException {
        return new Date(json.getAsJsonPrimitive().getAsLong());
      }
    });

    Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    Auction auction = null;

    switch (auctionType) {
      case ENGLISH:
        auction = gson.fromJson(auctionInJSONFormat.toString(), EnglishAuction.class);
        break;
      case DUTCH:
        auction = gson.fromJson(auctionInJSONFormat.toString(), DutchAuction.class);
        break;
      case JAPANESE:
        auction = gson.fromJson(auctionInJSONFormat.toString(), JapaneseAuction.class);
        break;
    }

    return auction;
  }

  @Override
  public Boolean isBuyerAlreadySubscribed() {
    return null;
  }

  @Override
  public void changeAuctionState(Integer auctionId, AuctionStateEnum auctionState) {
    ClientResource clientResource = new ClientResource(
        "http://localhost:8111/openbravo/auction/change_state");

    clientResource.addQueryParameter("auction_id", auctionId.toString())
        .addQueryParameter("auction_state", auctionState.toString());

    clientResource.post(clientResource);
  }
}
