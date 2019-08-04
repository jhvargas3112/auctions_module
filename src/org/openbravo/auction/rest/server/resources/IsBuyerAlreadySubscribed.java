package org.openbravo.auction.rest.server.resources;
/*
 * package org.openbravo.auction.rest.server.resources;
 * 
 * import java.util.HashMap; import java.util.TreeSet;
 * 
 * import org.openbravo.auction.model.Auction; import org.openbravo.auction.utils.ErrorResponseMsg;
 * import org.restlet.data.Status; import org.restlet.resource.Get; import
 * org.restlet.resource.ServerResource;
 * 
 * public class IsBuyerAlreadySubscribedRest extends ServerResource {
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Get public Boolean isBuyerAlreadySubscribedRest() { Integer auctionCode = Integer
 * .parseInt(getRequest().getAttributes().get("auction_code").toString()); String email =
 * getRequest().getAttributes().get("buyer_email").toString();
 * 
 * Boolean buyerIsAlreadySubscribed = false;
 * 
 * if (getContext().getAttributes().containsKey("auctions")) { HashMap<Integer, Auction> auctions =
 * (HashMap<Integer, Auction>) getContext().getAttributes() .get("auctions"); if
 * (auctions.containsKey(auctionCode)) { buyerIsAlreadySubscribed = ((HashMap<Integer, Auction>)
 * getContext().getAttributes()
 * .get("auctions")).get(auctionCode).getAuctionBuyers().contains(auctions) } else {
 * getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_CODE); } } else {
 * getResponse().setStatus(new Status(204), ErrorResponseMsg.WRONG_AUCTION_CODE); }
 * 
 * return ((TreeSet<String>) getContext().getAttributes().get("subscribed_buyers"))
 * .contains(getQueryValue("buyer_email")); }
 * 
 * }
 */
