package org.openbravo.auction.agents.behaviours;

import org.openbravo.auction.agents.DutchAuctioneerAgent;
import org.openbravo.auction.model.DutchAuction;
import org.openbravo.auction.service.impl.DutchAuctionServiceImpl;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

@SuppressWarnings("serial")
public class DecreaseCurrentPriceBehaviour extends TickerBehaviour {
  private Double amountToDecrease;
  private Integer roundsConsumed = 1;

  public DecreaseCurrentPriceBehaviour(Agent a, long period, Double amountToDecrease) {
    super(a, period);
    this.amountToDecrease = amountToDecrease;
  }

  @Override
  protected void onTick() {
    DutchAuction dutchAuction = ((DutchAuctioneerAgent) myAgent).getAuction();

    if (roundsConsumed <= dutchAuction.getNumberOfRounds()
        && dutchAuction.getStartingPrice() > dutchAuction.getMinimumSalePrice()) {
      System.out.println("SE REDUCE EL PRECIO EN " + amountToDecrease);
      dutchAuction.setStartingPrice(
          new DutchAuctionServiceImpl().reduceDutchAuctionItemPrice(amountToDecrease));
      ((DutchAuctioneerAgent) myAgent).setAuction(dutchAuction);
    }

    roundsConsumed++;
  }
}
