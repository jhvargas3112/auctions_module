package org.openbravo.auction.service.impl;

import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.service.EnglishAuctionService;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class EnglishAuctionServiceImpl implements EnglishAuctionService {
  @Override
  public EnglishAuctionBuyer determineEnglishAuctionWinner(EnglishAuction englishAuction) {
    return (EnglishAuctionBuyer) englishAuction.getAuctionBuyers().first();
  }
}
