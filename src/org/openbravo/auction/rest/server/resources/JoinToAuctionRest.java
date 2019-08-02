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

public class JoinToAuctionRest extends ServerResource {
  private OpenbravoAuctionService openbravoAuctionService = new OpenbravoAuctionServiceImpl();

  @SuppressWarnings("unchecked")
  @Post
  public void joinToAuction() {
    Integer auctionId = Integer.parseInt(getQueryValue("auction_id"));
    String email = getQueryValue("buyer_email");

    ArrayList<String> auctionBuyersEmails = ((HashMap<Integer, ArrayList<String>>) getContext()
        .getAttributes()
        .get("auction_emails")).get(auctionId);

    if (getContext().getAttributes().containsKey("auctions")) {
      HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
          .get("auctions");
      if (auctions.containsKey(auctionId)) {
        Auction auction = ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
            .get(auctionId);
        if (!auction.getAuctionState()
            .getAuctionStateEnum()
            .equals(AuctionStateEnum.IT_IS_CELEBRATING)) {

          if (auctionBuyersEmails.size() + 1 <= auction.getMaximumBiddersNum().intValue()) {

            if (!auctionBuyerExists(auctionId, email)) {
              Integer buyerId = generateBuyerId();

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
                  DutchAuction dutchAuction = ((DutchAuction) ((HashMap<Integer, Auction>) getContext()
                      .getAttributes()
                      .get("auctions")).get(auctionId));

                  TreeSet<DutchAuctionBuyer> dutchAuctionBuyers = (TreeSet<DutchAuctionBuyer>) dutchAuction
                      .getAuctionBuyers();

                  dutchAuctionBuyers.add(new DutchAuctionBuyer(buyerId, email));

                  dutchAuction.setAuctionBuyers(dutchAuctionBuyers);

                  ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
                      .put(auctionId, dutchAuction);
                  break;
                case JAPANESE:
                  JapaneseAuction japaneseAuction = ((JapaneseAuction) ((HashMap<Integer, Auction>) getContext()
                      .getAttributes()
                      .get("auctions")).get(auctionId));

                  TreeSet<JapaneseAuctionBuyer> japaneseAuctionBuyers = (TreeSet<JapaneseAuctionBuyer>) japaneseAuction
                      .getAuctionBuyers();

                  japaneseAuctionBuyers.add(new JapaneseAuctionBuyer(buyerId, email));

                  japaneseAuction.setAuctionBuyers(japaneseAuctionBuyers);

                  ((HashMap<Integer, Auction>) getContext().getAttributes().get("auctions"))
                      .put(auctionId, japaneseAuction);
                  break;
              }

              ((ArrayList<Integer>) getContext().getAttributes().get("buyer_ids")).add(buyerId);

              auctionBuyersEmails.add(email);

              ArrayList<Integer> auctionBuyersIds = ((HashMap<Integer, ArrayList<Integer>>) getContext()
                  .getAttributes()
                  .get("auction_buyerIds")).get(auctionId);

              auctionBuyersIds.add(buyerId);

              openbravoAuctionService.notifySubscription(auctionId, email);

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
  private Boolean auctionBuyerExists(Integer auctionId, String email) {
    ArrayList<String> auctionBuyersEmails = ((HashMap<Integer, ArrayList<String>>) getContext()
        .getAttributes()
        .get("auction_emails")).get(auctionId);

    return auctionBuyersEmails.contains(email);
  }

  @SuppressWarnings("unchecked")
  private Integer generateBuyerId() {
    ArrayList<Integer> buyerAuctions = ((ArrayList<Integer>) getContext().getAttributes()
        .get("buyer_ids"));

    int buyerId = RandomUtils.nextInt(100000, 999999);

    while (buyerAuctions.contains(buyerId)) {
      buyerId = RandomUtils.nextInt(100000, 999999);
    }

    return buyerId;
  }
}
