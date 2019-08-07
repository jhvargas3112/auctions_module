package org.openbravo.auction.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.auction.concurrence.ReduceAuctionItemPrice;
import org.openbravo.auction.concurrence.StartAuctionCelebration;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.AuctionBuyer;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.model.factory.AuctionFactory;
import org.openbravo.auction.rest.server.OpenbravoAuctionRestServer;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class OpenbravoAuctionServiceImpl implements OpenbravoAuctionService {
  @Override
  public void publishAuction(JSONObject jsonAuctionParameters) {
    Auction auction = (Auction) AuctionFactory.getAuction(jsonAuctionParameters);

    ClientResource clientResource = new ClientResource(
        "http://192.168.0.157:8111/openbravo/auction/publish");

    Representation JSONAuctionRepresentation = clientResource.put(auction);

    String auctionId = null;

    try {
      auctionId = JSONAuctionRepresentation.getText().toString();
    } catch (IOException e) {
      e.printStackTrace();
    }

    ArrayList<String> newAuctionNotificationMessageElements = new ArrayList<String>();
    newAuctionNotificationMessageElements.add(auction.toString());
    notifyBidders(newAuctionNotificationMessageElements, auctionId);

    new Thread(
        new StartAuctionCelebration(auctionId, auction.getCelebrationDate(), auction.getDeadLine()))
            .start();
  }

  @Override
  public void notifyBidders(ArrayList<String> newAuctionNotificationMessageElements,
      String auctionId) {
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

    String[] buyers = appProps.getProperty("bidders").split(",");

    notifyBidders(buyers, newAuctionNotificationMessageElements, auctionId);
  }

  @Override
  public void notifyBidders(String[] buyersEmails,
      ArrayList<String> newAuctionNotificationMessageElements, String auctionId) {
    sendEmailToBidders(buyersEmails, newAuctionNotificationMessageElements, auctionId);
  }

  @Override
  public void notifySubscription(String auctionId, String buyerId, String buyerEmail) {
    HashMap<String, Object> emailSenderParameters = getSenderEmailParameters();

    sendEmail((String) emailSenderParameters.get("emailFrom"), buyerEmail,
        (String) emailSenderParameters.get("password"),
        (String) emailSenderParameters.get("hostname"),
        Integer.parseInt((String) emailSenderParameters.get("smtp_port")),
        "Confirmación de inscripción a subasta",
        createNewSubscriptionNotificationMessage(auctionId, buyerId, buyerEmail));
  }

  @Override
  public void notifyAuctionWinner(String auctionId, String buyerEmail) {
    HashMap<String, Object> emailSenderParameters = getSenderEmailParameters();

    sendEmail((String) emailSenderParameters.get("emailFrom"), buyerEmail,
        (String) emailSenderParameters.get("password"),
        (String) emailSenderParameters.get("hostname"),
        Integer.parseInt((String) emailSenderParameters.get("smtp_port")),
        "¡Enhorabuena! usted ha sido ganador de la subasta",
        createWinnerNotificationMessage(auctionId, buyerEmail));
  }

  @Override
  public String createNewAuctionNotificationMessage(
      ArrayList<String> newAuctionNotificationMessageElements, String auctionId,
      String buyerEmail) {

    StringBuilder sb = new StringBuilder();

    for (String element : newAuctionNotificationMessageElements) {
      sb.append(element).append("\n");
    }

    sb.append("\n\n")
        .append("Apuntate a la subasta, haciendo click en el siguiente enlace: "
            + "http://192.168.0.157:8111/openbravo/auction/sign_up?auction_id=" + auctionId
            + "&buyer_email=" + buyerEmail);

    return sb.toString();
  }

  @Override
  public String createNewSubscriptionNotificationMessage(String auctionId, String buyerId,
      String buyerEmail) {
    Auction auction = getAuction(auctionId);

    String newSubscriptionNotificationMessage = "Usted acaba de inscribirse en la subasta que se celebrá el día "
        + auction.getCelebrationDate() + "."
        + "\n\nPara poder empezar a participar en la subasta en la fecha indicada, deberá acceder a la URL "
        + "http://192.168.0.157:8111/openbravo/auction/join?auction_id=" + auctionId
        + " e identificarse introduciendo el código " + buyerId
        + "\n\nA continuación, le recordamos la información de la subasta: \n\n"
        + auction.toString();

    return newSubscriptionNotificationMessage;
  }

  @Override
  public String createWinnerNotificationMessage(String auctionId, String buyerEmail) {
    Auction auction = getAuction(auctionId);

    String newSubscriptionNotificationMessage = "Enhorabuena, usted a resultado ganador de la subasta con código "
        + auctionId + ", con un precio de venta final de " + auction.getStartingPrice()
        + ". Nos pondremos en contacto con usted le indicaremos el proceso para que pueda adquirir el producto subastado."
        + "\nA continuación, le recordamos la información de la subasta que finalizó el día "
        + auction.getDeadLine() + ": \n\n" + auction.toString();

    return newSubscriptionNotificationMessage;
  }

  @Override
  public void startAuctionCelebration(String auctionId) {
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

        BigDecimal amountToDecreasePrice = dutchAuctionServiceImpl.getAmountToDecreasePrice(
            dutchAuction.getStartingPrice(), dutchAuction.getMinimumSalePrice(),
            dutchAuction.getNumberOfRounds());

        new Thread(new ReduceAuctionItemPrice(auctionId, dutchAuction.getStartingPrice(),
            dutchAuction.getMinimumSalePrice(), dutchAuction.getNumberOfRounds(),
            periodOfTimeToDecreasePrice, amountToDecreasePrice)).start();
        break;
      case JAPANESE:
        break;
    }
  }

  @Override
  public void finishAuctionCelebration(String auctionId) {
    changeAuctionState(auctionId, AuctionStateEnum.FINISHED);

    Auction auction = getAuction(auctionId);

    switch (auction.getAuctionType().getAuctionTypeEnum()) {
      case ENGLISH:
        EnglishAuctionBuyer winner = new EnglishAuctionServiceImpl()
            .determineEnglishAuctionWinner((EnglishAuction) auction);
        notifyAuctionWinner(auctionId, winner.getEmail());

        // TODO: Eliminar la subasta del contexto de restlet o pensar como solucionar eso, porque el
        // administrador tiene que guardarse la info para poder después contactar con el ganador.
        break;
      case DUTCH:
        break;
      case JAPANESE:
        break;
    }
  }

  @Override
  public void cancelAuctionCelebration(String auctionId) {
    changeAuctionState(auctionId, AuctionStateEnum.CANCELLED);
  }

  @Override
  public Auction getAuction(String auctionId) {
    ClientResource clientResource = new ClientResource(
        "http://192.168.0.157:8111/openbravo/auction/auction_info");
    clientResource.addQueryParameter("auction_id", auctionId.toString());

    Representation JSONAuctionRepresentation = clientResource.get();

    return getAuction(JSONAuctionRepresentation);
  }

  public Auction getAuction(Representation JSONAuctionRepresentation) {
    Auction auction = null;

    try {
      auction = AuctionFactory.getAuction(new JSONObject(JSONAuctionRepresentation.getText()));
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }

    return auction;
  }

  @SuppressWarnings("unchecked")
  @Override
  public TreeSet<AuctionBuyer> getAuctionBuyers(String auctionId) {
    return (TreeSet<AuctionBuyer>) getAuction(auctionId).getAuctionBuyers();
  }

  @Override
  public Integer countAuctionBuyers(String auctionId) {
    return getAuction(auctionId).getAuctionBuyers().size();
  }

  @Override
  public void changeAuctionState(String auctionId, AuctionStateEnum auctionState) {
    ClientResource clientResource = new ClientResource(
        "http://192.168.0.157:8111/openbravo/auction/change_state");
    clientResource.addQueryParameter("auction_id", auctionId.toString());
    clientResource.post(new StringRepresentation(auctionState.toString()));
  }

  @Override
  public void startOpenbravoAuctionRestServer() {
    if (!OpenbravoAuctionRestServer.INSTANCE.isStarted()) {
      OpenbravoAuctionRestServer.INSTANCE.start();
    }
  }

  // Private methods.

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

  private void sendEmailToBidders(String[] buyersEmails,
      ArrayList<String> newAuctionNotificationMessageElements, String auctionId) {
    HashMap<String, Object> emailSenderParameters = getSenderEmailParameters();

    for (int i = 0; i < buyersEmails.length; i++) {
      String buyerEmail = buyersEmails[i];

      sendEmail((String) emailSenderParameters.get("emailFrom"), buyerEmail,
          (String) emailSenderParameters.get("password"),
          (String) emailSenderParameters.get("hostname"),
          Integer.parseInt((String) emailSenderParameters.get("smtp_port")), "Nueva subasta",
          createNewAuctionNotificationMessage(newAuctionNotificationMessageElements, auctionId,
              buyerEmail));
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
}
