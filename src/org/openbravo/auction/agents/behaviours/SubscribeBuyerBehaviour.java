package org.openbravo.auction.agents.behaviours;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.time.DateUtils;
import org.openbravo.auction.agents.OpenbravoAuctionAgent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.StaleProxyException;

@SuppressWarnings("serial")
public class SubscribeBuyerBehaviour extends Behaviour {
  private Date celebrationDate;
  private MessageTemplate messageTemplate;

  public SubscribeBuyerBehaviour(Date celebrationDate) {
    this.celebrationDate = celebrationDate;
    messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
  }

  @Override
  public void action() {
    ACLMessage msg = myAgent.receive(messageTemplate);

    if (msg != null) {
      String buyerEmail = msg.getContent();

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

      String filepath = appProps.getProperty("registered_buyers_path_file")
          + "/registered_buyers.xml";
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder;

      Document document = null;

      try {
        docBuilder = docFactory.newDocumentBuilder();
        document = docBuilder.parse(filepath);

        Node root = document.getElementsByTagName("Root").item(0);

        Element buyer = document.createElement("buyer");

        Element email = document.createElement("email");
        email.setTextContent(buyerEmail);

        buyer.appendChild(email);

        root.appendChild(buyer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);

      } catch (SAXException e1) {
        e1.printStackTrace();
      } catch (IOException e2) {
        e2.printStackTrace();
      } catch (ParserConfigurationException e3) {
        e3.printStackTrace();
      } catch (TransformerConfigurationException e4) {
        e4.printStackTrace();
      } catch (TransformerException e) {
        e.printStackTrace();
      }

      System.out.println(
          "Se ha añadido al comprador " + buyerEmail + " al datasource XML de compradores ");

    } else {
      // block();
    }
  }

  /**
   * Checks if the auction has already begun.
   */
  @Override
  public boolean done() {
    return DateUtils.isSameInstant(celebrationDate, new Date());
  }

  @Override
  public int onEnd() {
    try {
      System.out.println("HOY " + new Date() + " EMPIEZA A CELEBRARSE LA SUBASTA");
      ((OpenbravoAuctionAgent) myAgent).getAuctioneerAgentController().start();
    } catch (StaleProxyException e) {
      e.printStackTrace();
    }

    myAgent.doDelete();
    return 0;
  }
}
