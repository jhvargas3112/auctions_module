package org.openbravo.auction.service;

import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public interface EnglishAuctionService {
  public Boolean CheckIfThereIsAWinner(EnglishAuction englishAuction);

  public void finishAuctionCelebration(String englishAuctionId);

  public EnglishAuctionBuyer determineEnglishAuctionWinner(EnglishAuction englishAuction);
}
