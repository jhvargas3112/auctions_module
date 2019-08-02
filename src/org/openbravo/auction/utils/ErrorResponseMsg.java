package org.openbravo.auction.utils;

public class ErrorResponseMsg {
  public static final String WRONG_AUCTION_ID = "There is no auction with that code";
  public static final String BUYER_IS_ALREADY_SUBSCRIBED = "The buyer is already subscribed in this auction";
  public static final String AUCTION_IS_ALREADY_CELEBRATING = "The auction is already celebrating";
  public static final String MAXIMUM_NUMBER_OF_BUYERS_EXCEEDED = "The maximum number of buyers has been exceeded for this auction";
  public static final String FEW_BUYERS = "The minimum number of buyers must be at least two";
}
