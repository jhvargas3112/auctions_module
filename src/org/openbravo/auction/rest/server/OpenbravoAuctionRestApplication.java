package org.openbravo.auction.rest.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.openbravo.auction.model.Auction;
import org.openbravo.auction.rest.server.resources.AuctionCelebrationAuthentication;
import org.openbravo.auction.rest.server.resources.AuctionInfo;
import org.openbravo.auction.rest.server.resources.AuctionState;
import org.openbravo.auction.rest.server.resources.ChangeAuctionState;
import org.openbravo.auction.rest.server.resources.GetAuctionBuyers;
import org.openbravo.auction.rest.server.resources.GetAuctions;
import org.openbravo.auction.rest.server.resources.PublishAuction;
import org.openbravo.auction.rest.server.resources.ReduceItemPrice;
import org.openbravo.auction.rest.server.resources.RequestToJoinToTheAuction;
import org.openbravo.auction.rest.server.resources.SignUpToTheAuction;
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

    getContext().getAttributes().put("auctions", new HashMap<String, Auction>());
    getContext().getAttributes().put("buyer_ids", new ArrayList<String>());
    getContext().getAttributes().put("auction_emails", new HashMap<String, ArrayList<String>>());
    getContext().getAttributes().put("auction_buyerIds", new HashMap<String, ArrayList<String>>());

    setName("RESTful Openbravo auctions module server");
    setDescription("This is the RESTful API for the Openbravo auctions module");
    setOwner("Jhonny Vargas and the Openbravo ERP community");
    setAuthor("Jhonny Vargas");
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void startOpenbravoAuctionRestServer() {
    ArrayList<Protocol> protocols = new ArrayList<Protocol>();
    protocols.add(Protocol.HTTP);

    OpenbravoAuctionRestApplication openbravoAuctionRestApplication = new OpenbravoAuctionRestApplication();

    Server openbravoAuctionServer = new Server(protocols, 8111, openbravoAuctionRestApplication);

    CorsService corsService = new CorsService();
    corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
    corsService.setAllowedCredentials(true);

    openbravoAuctionRestApplication.getServices().add(corsService);

    try {
      openbravoAuctionServer.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Restlet createInboundRoot() {
    Router router = new Router(getContext());

    router.attach("http://192.168.0.157:8111/openbravo/auction/publish", PublishAuction.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/auctions", GetAuctions.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/sign_up", SignUpToTheAuction.class);
    // router.attach("http://localhost:8111/openbravo/auction/leave", OpenbravoAuctionRest.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/buyers", GetAuctionBuyers.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/auction_info", AuctionInfo.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/auction_state", AuctionState.class);
    // router.attach("http://localhost:8111/openbravo/auction/buyer_already_exists",
    // IsBuyerAlreadySubscribedRest.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/change_state",
        ChangeAuctionState.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/join",
        RequestToJoinToTheAuction.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/celebration",
        AuctionCelebrationAuthentication.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/reduce_item_price",
        ReduceItemPrice.class);

    return router;
  }
}