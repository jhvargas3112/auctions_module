package org.openbravo.auction.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.openbravo.auction.agents.OpenbravoAuctionAgentContainer;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.Item;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class OpenbravoAuctionServiceImpl implements OpenbravoAuctionService {

  @Override
  public Auction createAuction(String auctionType, Date celebrationDate, Date deadLine,
      Integer maximumBiddersNum, Item item, Double startingPrice, Double minimumSalePrice,
      String additionalInformation) {

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

  // FIXME: Dividir este método en 2: 1 para obtener los parametros del formulario y otro para
  // obtener el Item.
  @Override
  public void publishAuction(JSONObject jsonAuctionParameters) {
    OpenbravoAuctionAgentContainer.INSTANCE.getValue();

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

      // FIXME: Tener en cuenta los valores nulos, sobre todo en volume y weight (Y EN TOOS EN
      // REALIDAD) y ponerle 0.0, "", o lo que corresponda por
      // defecto, por ejemplo, o mejor ponerlo a null

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

    Auction auction = createAuction(auctionType, celebrationDate, deadLine, maximumBiddersNum, item,
        startingPrice, minimumSalePrice, additionalInformation);

    AgentController openbravoAuctionAgent = null;

    Object[] openbravoAuctionAgentAurguments = new Object[1];
    openbravoAuctionAgentAurguments[0] = auction;

    try {
      openbravoAuctionAgent = OpenbravoAuctionAgentContainer.INSTANCE.getValue()
          .createNewAgent("openbravo_auction", "org.openbravo.auction.agents.OpenbravoAuctionAgent",
              openbravoAuctionAgentAurguments);

      openbravoAuctionAgent.start();

    } catch (StaleProxyException e) {
      e.printStackTrace();
    } catch (ControllerException e) {
      e.printStackTrace();
    }

    // CREATE XML BUYERS FILE

    try {

      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

      Document document = documentBuilder.newDocument();

      // root element
      Element root = document.createElement("Root");

      document.appendChild(root);

      // create the xml file
      // transform the DOM Object to an XML File
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
  public void notifyBidders(ArrayList<String> newAuctionNotificationMessageElements) {
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

    notifyBidders(receivers, newAuctionNotificationMessageElements);
  }

  @Override
  public void notifyBidders(String[] receivers,
      ArrayList<String> newAuctionNotificationMessageElements) {
    sendEmailToBidders(receivers, newAuctionNotificationMessageElements);
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
      ArrayList<String> newAuctionNotificationMessageElements) {
    HashMap<String, Object> emailSenderParameters = getSenderEmailParameters();

    for (int i = 0; i < receivers.length; i++) {
      String receiveEmail = receivers[i];

      sendEmail((String) emailSenderParameters.get("emailFrom"), receiveEmail,
          (String) emailSenderParameters.get("password"),
          (String) emailSenderParameters.get("hostname"),
          Integer.parseInt((String) emailSenderParameters.get("smtp_port")), "Nueva subasta",
          createNewAuctionNotificationMessage(newAuctionNotificationMessageElements, receiveEmail));
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
      ArrayList<String> newAuctionNotificationMessageElements, String receiverEmail) {

    StringBuilder sb = new StringBuilder();

    for (String element : newAuctionNotificationMessageElements) {
      sb.append(element).append("\n");
    }

    sb.append("\n\n")
        .append("Apuntate a la subasta, haciendo click en el siguiente link: "
            + "http://172.31.99.66:8111/openbravo/auction/join_to?buyer_email=" + receiverEmail);

    return sb.toString();
  }

  @Override
  public void registerBuyerToAuction(String email) {
    Object[] openbravoAuctionAgentAurguments = new Object[1];
    openbravoAuctionAgentAurguments[0] = email;

    AgentController buyerAgent;

    try {
      buyerAgent = OpenbravoAuctionAgentContainer.INSTANCE.getValue()
          .createNewAgent(email, "org.openbravo.auction.agents.BuyerAgent",
              openbravoAuctionAgentAurguments);

      buyerAgent.start();
    } catch (StaleProxyException e) {
      e.printStackTrace();
    } catch (ControllerException e) {
      e.printStackTrace();
    }
  }

}
