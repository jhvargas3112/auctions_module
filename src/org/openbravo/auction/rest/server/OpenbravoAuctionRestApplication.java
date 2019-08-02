package org.openbravo.auction.rest.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.rest.server.resources.AuctionCelebrationRest;
import org.openbravo.auction.rest.server.resources.AuctionInfoRest;
import org.openbravo.auction.rest.server.resources.AuctionStateRest;
import org.openbravo.auction.rest.server.resources.ChangeAuctionStateRest;
import org.openbravo.auction.rest.server.resources.GetAuctionBuyersRest;
import org.openbravo.auction.rest.server.resources.GetAuctionsRest;
import org.openbravo.auction.rest.server.resources.JoinToAuctionRest;
import org.openbravo.auction.rest.server.resources.PublishAuctionRest;
import org.openbravo.auction.rest.server.resources.ReduceItemPriceRest;
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

  public OpenbravoAuctionRestApplication() {
    setContext(new Context());
    getContext().setAttributes(new HashMap<String, Object>());

    getContext().getAttributes().put("auctions", new HashMap<Integer, Auction>());
    getContext().getAttributes().put("buyer_ids", new ArrayList<Integer>());
    getContext().getAttributes().put("auction_emails", new HashMap<Integer, ArrayList<String>>());
    getContext().getAttributes()
        .put("auction_buyerIds", new HashMap<Integer, ArrayList<Integer>>());

    setName("RESTful Openbravo auctions module server");
    setDescription("This is the RESTful API for the Openbravo auctions module");
    setOwner("Jhonny Vargas and the Openbravo ERP community");
    setAuthor("Jhonny Vargas");
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void startOpenbravoAuctionRestServer() {
    Server mailServer = new Server(Protocol.HTTP, 8111);

    OpenbravoAuctionRestApplication openbravoAuctionRestApplication = new OpenbravoAuctionRestApplication();

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

    router.attach("http://localhost:8111/openbravo/auction/publish", PublishAuctionRest.class);
    router.attach("http://localhost:8111/openbravo/auction/auctions", GetAuctionsRest.class);
    router.attach("http://localhost:8111/openbravo/auction/join", JoinToAuctionRest.class);
    // router.attach("http://localhost:8111/openbravo/auction/leave", OpenbravoAuctionRest.class);
    router.attach("http://localhost:8111/openbravo/auction/buyers", GetAuctionBuyersRest.class);
    router.attach("http://localhost:8111/openbravo/auction/auction_info", AuctionInfoRest.class);
    router.attach("http://localhost:8111/openbravo/auction/auction_state", AuctionStateRest.class);
    // router.attach("http://localhost:8111/openbravo/auction/buyer_already_exists",
    // IsBuyerAlreadySubscribedRest.class);
    router.attach("http://localhost:8111/openbravo/auction/change_state",
        ChangeAuctionStateRest.class);
    router.attach("http://localhost:8111/openbravo/auction/celebration",
        AuctionCelebrationRest.class);
    router.attach("http://localhost:8111/openbravo/auction/reduce_item_price",
        ReduceItemPriceRest.class);

    return router;
  }

}
