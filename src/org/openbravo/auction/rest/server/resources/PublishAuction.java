package org.openbravo.auction.rest.server.resources;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.RandomUtils;
import org.openbravo.auction.model.Auction;
import org.openbravo.auction.service.impl.OpenbravoAuctionServiceImpl;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class PublishAuction extends ServerResource {
  @SuppressWarnings("unchecked")
  @Put("json")
  public Integer publishAuction(Representation JSONAuctionRepresentation) {
    HashMap<String, Auction> auctions = (HashMap<String, Auction>) getContext().getAttributes()
        .get("auctions");

    Integer auctionId = RandomUtils.nextInt(100000, 999999);

    while (auctions.containsKey(auctionId.toString())) {
      auctionId = RandomUtils.nextInt(100000, 999999);
    }

    auctions.put(auctionId.toString(),
        new OpenbravoAuctionServiceImpl().getAuction(JSONAuctionRepresentation));

    ((HashMap<String, ArrayList<String>>) getContext().getAttributes().get("auction_emails"))
        .put(auctionId.toString(), new ArrayList<String>());

    ((HashMap<String, ArrayList<String>>) getContext().getAttributes().get("auction_buyerIds"))
        .put(auctionId.toString(), new ArrayList<String>());

    getResponse().setStatus(new Status(201));

    return auctionId;
  }
}
