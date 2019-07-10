package org.openbravo.auction.agents;

import org.openbravo.auction.agents.behaviours.DecreaseCurrentPriceBehaviour;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.service.impl.DutchAuctionServiceImpl;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class DutchAuctioneerAgent extends OpenbravoAgent {
  private DutchAuction dutchAuction;

  @Override
  public void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      dutchAuction = (DutchAuction) args[0];
      registerAgent("DUTCH-AUCTIONEER", this.getLocalName());
    }

    DutchAuctionServiceImpl dutchAuctionServiceImpl = new DutchAuctionServiceImpl();
    Long periodOfTimeToDecreasePrice = dutchAuctionServiceImpl.getPeriodOfTimeToDecreasePrice(
        dutchAuction.getCelebrationDate(), dutchAuction.getDeadLine(),
        dutchAuction.getNumberOfRounds());
    Double amountToDecreasePrice = dutchAuctionServiceImpl.getAmountToDecreasePrice(
        dutchAuction.getStartingPrice(), dutchAuction.getMinimumSalePrice(),
        dutchAuction.getNumberOfRounds());

    addBehaviour(new DecreaseCurrentPriceBehaviour(this, periodOfTimeToDecreasePrice,
        amountToDecreasePrice));

  }

  @Override
  protected void takeDown() {
  }

  public DutchAuction getAuction() {
    return dutchAuction;
  }

  public void setAuction(DutchAuction auction) {
    this.dutchAuction = auction;
  }

}
