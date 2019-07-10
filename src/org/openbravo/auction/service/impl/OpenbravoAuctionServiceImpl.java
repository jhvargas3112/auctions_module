package org.openbravo.auction.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
import org.openbravo.auction.model.factory.AuctionFactory;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.utils.AuctionTypeEnum;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
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
  public void publishAuction(JSONObject jsonAuctionParameters) {
    // Create the appropriate auction instance.
    Auction auction = (Auction) AuctionFactory.getAuction(jsonAuctionParameters);

    // Create the JADE agent.
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

    // Create the XML buyers file.
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
            + "http://localhost:8111/openbravo/auction/join_to?buyer_email=" + receiverEmail);

    return sb.toString();
  }

  @Override
  public void subscribeTheBuyerToAuction(String buyerEmail) {
    Object[] openbravoAuctionAgentAurguments = new Object[1];
    openbravoAuctionAgentAurguments[0] = buyerEmail;

    AgentController buyerAgent;

    try {
      buyerAgent = OpenbravoAuctionAgentContainer.INSTANCE.getValue()
          .createNewAgent(buyerEmail, "org.openbravo.auction.agents.BuyerAgent",
              openbravoAuctionAgentAurguments);

      buyerAgent.start();
    } catch (ControllerException e) {
      e.printStackTrace();
    }
  }

  @Override
  public AuctionTypeEnum getAuctionType() {
    Representation JSONAuctionRepresentation = new ClientResource(
        "http://localhost:8111/openbravo/auction/auction_info").get();

    AuctionTypeEnum auctionType = null;

    try {
      JSONObject auctionInJSONFormat = new JSONObject(JSONAuctionRepresentation.getText());
      auctionType = AuctionTypeEnum.valueOf(
          (String) ((JSONObject) auctionInJSONFormat.get("auctionType")).get("auctionTypeEnum"));

    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return auctionType;
  }

  @Override
  public Boolean isBuyerAlreadySubscribed() {
    return null;
  }

}
