package org.openbravo.auction.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

public class XMLUtils {
  public void saveAuctionWinner(String auctionId, String auctionFinishDate, String email,
      String item, BigDecimal price) {
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

    String filepath = appProps.getProperty("auction_winners_path_file") + "/auction_winners.xml";
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder;

    Document document = null;

    try {
      docBuilder = docFactory.newDocumentBuilder();
      document = docBuilder.parse(filepath);

      Node root = document.getElementsByTagName("Root").item(0);

      Element winner = document.createElement("winner");

      Element auction_id = document.createElement("auction_id");
      auction_id.setTextContent(auctionId);

      winner.appendChild(auction_id);

      Element finish_date = document.createElement("finish_date");
      finish_date.setTextContent(auctionFinishDate);

      winner.appendChild(finish_date);

      Element buyerEmail = document.createElement("email");
      buyerEmail.setTextContent(email);

      winner.appendChild(buyerEmail);

      Element itemName = document.createElement("item");
      itemName.setTextContent(item);

      winner.appendChild(itemName);

      Element salePrice = document.createElement("startingPrice");
      salePrice.setTextContent(price.toString() + " €");

      winner.appendChild(salePrice);

      root.appendChild(winner);

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
    } catch (TransformerException e5) {
      e5.printStackTrace();
    }
  }

  public void removeAuctionWinner(String auctionId, String email) {
    ClassLoader classLoader = getClass().getClassLoader();
    Properties appProps = new Properties();

    try {

      String resource = classLoader.getResource("config.properties").getPath();
      appProps.load(new FileInputStream(resource));

      String filepath = appProps.getProperty("auction_winners_path_file") + "/auction_winners.xml";
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder;

      Document document = null;

      docBuilder = docFactory.newDocumentBuilder();
      document = docBuilder.parse(filepath);

      DocumentTraversal traversal = (DocumentTraversal) document;

      Node root = document.getDocumentElement();

      NodeIterator iterator = traversal.createNodeIterator(root, NodeFilter.SHOW_ELEMENT, null,
          true);
      Element winner = null;

      int matches = 0;

      for (Node node = iterator.nextNode(); node != null; node = iterator.nextNode()) {
        Element element = (Element) node;

        if ("winner".equals(element.getTagName())) {
          winner = element;
        } else if ("auction_id".equals(element.getTagName())
            && auctionId.equals(element.getTextContent())) {
          matches++;
        } else if ("email".equals(element.getTagName()) && email.equals(element.getTextContent())) {
          matches++;
        }

        if (matches == 2) {
          root.removeChild(winner);
          matches = 0;
        }
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(new File(filepath));
      transformer.transform(source, result);
    } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }
}
