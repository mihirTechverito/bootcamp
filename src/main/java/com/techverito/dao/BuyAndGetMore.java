package com.techverito.dao;

public class BuyAndGetMore implements Offer {

  private final int offerOnQuantity;
  private final int offerOfQuantity;

  public BuyAndGetMore(int offerOn, int offerOff) {
    this.offerOnQuantity = offerOn;
    this.offerOfQuantity = offerOff;
  }

  @Override
  public int applyOffer(int quantity, int price) {
    int countAfterOfferApplied = offerOnQuantity + offerOfQuantity;

    return (price * offerOnQuantity* (quantity / countAfterOfferApplied))
        + (quantity % countAfterOfferApplied * price);
  }
}
