package org.openbravo.auction.rest.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.openbravo.auction.service.OpenbravoAuctionService;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.restlet.data.Status;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class OpenbravoAuctionRest extends ServerResource {
  private OpenbravoAuctionService openbravoAuctionService = new OpenbravoAuctionServiceImpl();

  @Override
  public Representation handle() {
    String requestPath = getRequest().getOriginalRef().getPath();

    String buyerEmail = null;

    if (StringUtils.equals(requestPath, "/openbravo/auction/join_to")) {
      buyerEmail = getQueryValue("buyer_email");
      joinToAuction(buyerEmail);
    } else {
      buyerEmail = getQueryValue("buyer_email");
      auctionLeave();
    }

    return new EmptyRepresentation();
  }

  // FIXME: tiene que recibir un nuevo parámetro, que será el token
  @SuppressWarnings("unchecked")
  @Post
  public void joinToAuction(String buyerEmail) {
    boolean buyerAlreadyExists = false;

    try {
      Representation buyerAlreadyExistsRepresentation = new ClientResource(
          "http://localhost:8111/openbravo/auction/buyer_already_exists?buyer_email=" + buyerEmail)
              .get();

      buyerAlreadyExists = Boolean.parseBoolean(buyerAlreadyExistsRepresentation.getText());
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (!buyerAlreadyExists) {
      openbravoAuctionService.subscribeTheBuyerToAuction(buyerEmail);

      HashMap<Integer, String> registeredBuyers = ((HashMap<Integer, String>) getContext()
          .getAttributes()
          .get("registered_buyers"));

      int registeredBuyerCode = RandomUtils.nextInt(100000, 999999);

      while (true) {
        if (!registeredBuyers.containsKey(registeredBuyerCode)) {
          break;
        }
        registeredBuyerCode = RandomUtils.nextInt(100000, 999999);
      }

      ((TreeSet<String>) getContext().getAttributes().get("subscribed_buyers")).add(buyerEmail);

      ((HashMap<Integer, String>) getContext().getAttributes().get("registered_buyers"))
          .put(registeredBuyerCode, buyerEmail);

      getResponse().setStatus(new Status(200));
    } else {
      getResponse().setStatus(new Status(200));
    }
  }

  @Post
  public void auctionLeave() {
    getResponse().setStatus(new Status(200));
  }

}
