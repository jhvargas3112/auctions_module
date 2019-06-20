package org.openbravo.auction.rest;

import org.openbravo.auction.rest.service.OpenbravoAuctionRest;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class OpenbravoAuctionRestApplication extends Application {

  public OpenbravoAuctionRestApplication() {
    setName("RESTful Openbravo auctions module server");
    setDescription("This is the RESTful API for the Openbravo auctions module");
    setOwner("Jhonny Vargas and the OPenbravo ERP community");
    setAuthor("Jhonny Vargas");
  }

  public static void startOpenbravoAuctionRestServer() {
    Server mailServer = new Server(Protocol.HTTP, 8111);
    mailServer.setNext(new OpenbravoAuctionRestApplication());

    try {
      mailServer.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Restlet createInboundRoot() {
    Router router = new Router(getContext());
    router.attach("http://192.168.0.157:8111/openbravo/auction/join_to",
        OpenbravoAuctionRest.class);
    router.attach("http://192.168.0.157:8111/openbravo/auction/leave_to",
        OpenbravoAuctionRest.class);

    /*
     * return new Restlet() {
     * 
     * @Override public void handle(Request request, Response response) { String entity =
     * "Method : " + request.getMethod() + "\nResource URI : " + request.getResourceRef() +
     * "\nIP address : " + request.getClientInfo().getAddress() + "\nAgent name : " +
     * request.getClientInfo().getAgentName() + "\nAgent version: " +
     * request.getClientInfo().getAgentVersion(); response.setEntity(entity, MediaType.TEXT_PLAIN);
     * } };
     */

    return router;
  }

}
