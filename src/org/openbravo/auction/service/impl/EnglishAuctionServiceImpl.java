package org.openbravo.auction.service.impl;

import java.math.BigDecimal;

import org.openbravo.auction.model.EnglishAuction;
import org.openbravo.auction.model.EnglishAuctionBuyer;
import org.openbravo.auction.service.EnglishAuctionService;
import org.openbravo.auction.utils.AuctionStateEnum;
import org.openbravo.auction.utils.XMLUtils;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class EnglishAuctionServiceImpl implements EnglishAuctionService {
  @Override
  public Boolean CheckIfThereIsAWinner(EnglishAuction englishAuction) {
    EnglishAuctionBuyer candidateWinner = (EnglishAuctionBuyer) englishAuction.getAuctionBuyers()
        .first();
    BigDecimal candidateWinnerLastOffer = candidateWinner.getLastOffer();

    return !candidateWinnerLastOffer.equals(new BigDecimal(0))
        && candidateWinnerLastOffer.compareTo(englishAuction.getStartingPrice()) == 1;
  }

  @Override
  public void finishAuctionCelebration(String englishAuctionId) {
    OpenbravoAuctionServiceImpl openbravoAuctionServiceImpl = new OpenbravoAuctionServiceImpl();

    EnglishAuction englishAuction = (EnglishAuction) new OpenbravoAuctionServiceImpl()
        .getAuction(englishAuctionId);

    EnglishAuctionServiceImpl englishAuctionServiceImpl = new EnglishAuctionServiceImpl();

    if (englishAuctionServiceImpl.CheckIfThereIsAWinner((EnglishAuction) englishAuction)) {
      openbravoAuctionServiceImpl.changeAuctionState(englishAuctionId,
          AuctionStateEnum.FINISHED_WITH_WINNER);

      EnglishAuctionBuyer winner = new EnglishAuctionServiceImpl()
          .determineEnglishAuctionWinner((EnglishAuction) englishAuction);
      openbravoAuctionServiceImpl.notifyAuctionWinner(englishAuctionId, winner.getEmail());

      new XMLUtils().saveAuctionWinner(englishAuctionId, englishAuction.getDeadLine().toString(),
          winner.getEmail(), englishAuction.getItem().getName(), englishAuction.getCurrentPrice());
    } else {
      openbravoAuctionServiceImpl.changeAuctionState(englishAuctionId,
          AuctionStateEnum.FINISHED_WITHOUT_WINNER);
    }
  }

  @Override
  public EnglishAuctionBuyer determineEnglishAuctionWinner(EnglishAuction englishAuction) {
    EnglishAuctionBuyer englishAuctionBuyer = null;

    if (CheckIfThereIsAWinner(englishAuction)) {
      englishAuctionBuyer = (EnglishAuctionBuyer) englishAuction.getAuctionBuyers().first();
    }

    return englishAuctionBuyer;
  }
}
