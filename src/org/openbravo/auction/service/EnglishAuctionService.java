package org.openbravo.auction.service;

import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public interface EnglishAuctionService {

  public EnglishAuctionBuyer determineEnglishAuctionWinner(EnglishAuction englishAuction);

}
