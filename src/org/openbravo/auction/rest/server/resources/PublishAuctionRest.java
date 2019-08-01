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

public class PublishAuctionRest extends ServerResource {
  @SuppressWarnings("unchecked")
  @Put("json")
  public Integer publishAuction(Representation JSONAuctionRepresentation) {
    HashMap<Integer, Auction> auctions = (HashMap<Integer, Auction>) getContext().getAttributes()
        .get("auctions");

    int auctionId = RandomUtils.nextInt(100000, 999999);

    while (auctions.containsKey(auctionId)) {
      auctionId = RandomUtils.nextInt(100000, 999999);
    }

    auctions.put(auctionId,
        new OpenbravoAuctionServiceImpl().getAuction(JSONAuctionRepresentation));

    ((HashMap<Integer, ArrayList<String>>) getContext().getAttributes().get("auction_emails"))
        .put(auctionId, new ArrayList<String>());

    ((HashMap<Integer, ArrayList<Integer>>) getContext().getAttributes().get("auction_buyerIds"))
        .put(auctionId, new ArrayList<Integer>());

    getResponse().setStatus(new Status(201));

    return auctionId;
  }
}
