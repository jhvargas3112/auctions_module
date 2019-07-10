package org.openbravo.auction.agents;

import org.openbravo.auction.model.JapaneseAuction;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class JapaneseAuctioneerAgent extends OpenbravoAgent {
  private JapaneseAuction japaneseAuction;

  @Override
  public void setup() {
    Object[] args = getArguments();

    if (args != null && args.length > 0) {
      japaneseAuction = (JapaneseAuction) args[0];
      registerAgent("JAPANESE-AUCTIONEER", this.getLocalName());
    }

    /*
     * DutchAuctionServiceImpl dutchAuctionServiceImpl = new DutchAuctionServiceImpl(); Long
     * periodOfTimeToDecreasePrice = dutchAuctionServiceImpl.getPeriodOfTimeToDecreasePrice(
     * japaneseAuction.getCelebrationDate(), japaneseAuction.getDeadLine(),
     * japaneseAuction.getNumberOfRounds()); Double amountToDecreasePrice =
     * dutchAuctionServiceImpl.getAmountToDecreasePrice( japaneseAuction.getStartingPrice(),
     * japaneseAuction.getMinimumSalePrice(), japaneseAuction.getNumberOfRounds());
     * 
     * addBehaviour(new DecreaseCurrentPriceBehaviour(this, periodOfTimeToDecreasePrice,
     * amountToDecreasePrice));
     */

  }

  @Override
  protected void takeDown() {
  }

  public JapaneseAuction getAuction() {
    return japaneseAuction;
  }

  public void setAuction(JapaneseAuction auction) {
    this.japaneseAuction = auction;
  }

}
