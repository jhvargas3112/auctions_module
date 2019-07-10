package org.openbravo.auction.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.rest.service.AuctionCelebrationRest;
import org.openbravo.auction.rest.service.AuctionInfoRest;
import org.openbravo.auction.rest.service.AuctionStateRest;
import org.openbravo.auction.rest.service.IsBuyerAlreadySubscribedRest;
import org.openbravo.auction.rest.service.OpenbravoAuctionRest;
import org.openbravo.auction.rest.service.ReduceItemPriceRest;
import org.openbravo.auction.rest.service.RegisteredBuyersRest;
import org.openbravo.auction.utils.AuctionState;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class OpenbravoAuctionRestApplication extends Application {

  public OpenbravoAuctionRestApplication(Auction auction) {
    setContext(new Context());
    getContext().setAttributes(new HashMap<String, Object>());
    getContext().getAttributes().put("subscribed_buyers", new TreeSet<String>());
    getContext().getAttributes().put("registered_buyers", new HashMap<Integer, String>()); // <token,
                                                                                           // email_comprador>
    getContext().getAttributes().put("auction", auction);
    getContext().getAttributes().put("auctionState", AuctionState.PUBLISHED);

    setName("RESTful Openbravo auctions module server");
    setDescription("This is the RESTful API for the Openbravo auctions module");
    setOwner("Jhonny Vargas and the Openbravo ERP community");
    setAuthor("Jhonny Vargas");
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static void startOpenbravoAuctionRestServer(Auction auction) {
    Server mailServer = new Server(Protocol.HTTP, 8111);

    OpenbravoAuctionRestApplication openbravoAuctionRestApplication = new OpenbravoAuctionRestApplication(
        auction);

    CorsService corsService = new CorsService();
    corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
    corsService.setAllowedCredentials(true);

    openbravoAuctionRestApplication.getServices().add(corsService);

    mailServer.setNext(openbravoAuctionRestApplication);

    try {
      mailServer.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Restlet createInboundRoot() {
    Router router = new Router(getContext());

    router.attach("http://localhost:8111/openbravo/auction/join_to", OpenbravoAuctionRest.class);
    router.attach("http://localhost:8111/openbravo/auction/leave_to", OpenbravoAuctionRest.class);
    router.attach("http://localhost:8111/openbravo/auction/registered_buyers",
        RegisteredBuyersRest.class);
    router.attach("http://localhost:8111/openbravo/auction/auction_info", AuctionInfoRest.class);
    router.attach("http://localhost:8111/openbravo/auction/auction_state", AuctionStateRest.class);
    router.attach("http://localhost:8111/openbravo/auction/buyer_already_exists",
        IsBuyerAlreadySubscribedRest.class);
    router.attach("http://localhost:8111/openbravo/auction/celebration", AuctionCelebrationRest.class);
    router.attach("http://localhost:8111/openbravo/auction/reduce_item_price",
        ReduceItemPriceRest.class);

    return router;
  }

}
