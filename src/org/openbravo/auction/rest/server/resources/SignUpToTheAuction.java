package org.openbravo.auction.rest.server.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.apache.commons.lang3.RandomUtils;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.model.DutchAuctionBuyer;
import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.model.JapaneseAuction;
import org.openbravo.auction.model.JapaneseAuctionBuyer;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.ErrorResponseMsg;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class SignUpToTheAuction extends ServerResource {
  private OpenbravoAuctionService openbravoAuctionService = new OpenbravoAuctionServiceImpl();

  @SuppressWarnings("unchecked")
  @Post // ESTO DEBERIA IR POR GET (DEVOLVIENDO UN BOOLEANO) PORQUE NO PUEDE UNIRME DESDE EL MENSAJE
        // RECIBIDO EN EL CORREO ELECTRÓNICO
  public void joinToAuction() {
    String auctionId = getQueryValue("auction_id");
    String email = getQueryValue("buyer_email");

    ArrayList<String> auctionBuyersEmails = ((HashMap<String, ArrayList<String>>) getContext()
        .getAttributes()
        .get("auction_emails")).get(auctionId);

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        Auction auction = ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);
        if (!auction.getAuctionState()
            .getAuctionStateEnum()
            .equals(AuctionStateEnum.IT_IS_CELEBRATING)) {
          if (!auction.getAuctionState().getAuctionStateEnum().equals(AuctionStateEnum.CANCELLED)) {
            if (auctionBuyersEmails.size() + 1 <= auction.getMaximumBiddersNum().intValue()) {
              if (!auctionBuyerExists(auctionId, email)) {
                String buyerId = generateBuyerId();

                switch (auction.getAuctionType().getAuctionTypeEnum()) {
                  case ENGLISH:
                    EnglishAuction englishAuction = (EnglishAuction) auctions.get(auctionId);

                    TreeSet<EnglishAuctionBuyer> englishAuctionBuyers = (TreeSet<EnglishAuctionBuyer>) englishAuction
                        .getAuctionBuyers();

                    englishAuctionBuyers
                        .add(new EnglishAuctionBuyer(buyerId, email, new BigDecimal(0.0)));

                    englishAuction.setAuctionBuyers(englishAuctionBuyers);

                    auctions.put(auctionId, englishAuction);
                    break;
                  case DUTCH:
                    DutchAuction dutchAuction = ((DutchAuction) ((HashMap<String, Auction>) getContext()
                        .getAttributes()
                        .get("auctions")).get(auctionId));

                    TreeSet<DutchAuctionBuyer> dutchAuctionBuyers = (TreeSet<DutchAuctionBuyer>) dutchAuction
                        .getAuctionBuyers();

                    dutchAuctionBuyers.add(new DutchAuctionBuyer(buyerId, email));

                    dutchAuction.setAuctionBuyers(dutchAuctionBuyers);

                    ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
                        .put(auctionId, dutchAuction);
                    break;
                  case JAPANESE:
                    JapaneseAuction japaneseAuction = ((JapaneseAuction) ((HashMap<String, Auction>) getContext()
                        .getAttributes()
                        .get("auctions")).get(auctionId));

                    TreeSet<JapaneseAuctionBuyer> japaneseAuctionBuyers = (TreeSet<JapaneseAuctionBuyer>) japaneseAuction
                        .getAuctionBuyers();

                    japaneseAuctionBuyers.add(new JapaneseAuctionBuyer(buyerId, email));

                    japaneseAuction.setAuctionBuyers(japaneseAuctionBuyers);

                    ((HashMap<String, Auction>) getContext().getAttributes().get("auctions"))
                        .put(auctionId, japaneseAuction);
                    break;
                }

                ((ArrayList<String>) getContext().getAttributes().get("buyer_ids")).add(buyerId);

                auctionBuyersEmails.add(email);

                ArrayList<String> auctionBuyersIds = ((HashMap<String, ArrayList<String>>) getContext()
                    .getAttributes()
                    .get("auction_buyerIds")).get(auctionId);

                auctionBuyersIds.add(buyerId);

                openbravoAuctionService.notifyBuyerSubscription(auctionId, buyerId, email);

                System.out.println(
                    "Se ha añadido al comprador " + email + " al datasource XML de compradores ");

                getResponse().setStatus(new Status(200));
              } else {
                // TODO: NOTIFICAR AL COMPRADOR QUE YA ESTÁ REGISTRADO EN LA SUBASTA.
                getResponse().setStatus(new Status(422),
                    ErrorResponseMsg.BUYER_IS_ALREADY_SUBSCRIBED);
              }
            } else {
              // TODO: NOTIFICAR AL COMPRADOR QUE YA SE HA SUPERADO EL NÚMERO MÁXIMO DE COMPRADORES
              // PARA ESTA SUBASTA.
              getResponse().setStatus(new Status(422),
                  ErrorResponseMsg.MAXIMUM_NUMBER_OF_BUYERS_EXCEEDED);
            }
          } else {
            getResponse().setStatus(new Status(422), ErrorResponseMsg.AUCTION_HAS_BEEN_CANCELLED);
          }
        } else {
          getResponse().setStatus(new Status(422), ErrorResponseMsg.AUCTION_IS_ALREADY_CELEBRATING);
        }
      } else {
        getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
      }
    } else {
      getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_ID);
    }
  }

  @SuppressWarnings("unchecked")
  private Boolean auctionBuyerExists(String auctionId, String email) {
    ArrayList<String> auctionBuyersEmails = ((HashMap<String, ArrayList<String>>) getContext()
        .getAttributes()
        .get("auction_emails")).get(auctionId);

    return auctionBuyersEmails.contains(email);
  }

  @SuppressWarnings("unchecked")
  private String generateBuyerId() {
    ArrayList<String> buyerAuctions = ((ArrayList<String>) getContext().getAttributes()
        .get("buyer_ids"));

    Integer buyerId = RandomUtils.nextInt(100000, 999999);

    while (buyerAuctions.contains(buyerId.toString())) {
      buyerId = RandomUtils.nextInt(100000, 999999);
    }

    return buyerId.toString();
  }
}
