package com.techverito.dao;

public class InventoryProduct {

  private final int price;
  private Offer offer = Offer.None;

  public InventoryProduct(int price) {
    this.price = price;
  }

  public void activateOffer(Offer offer) {
    this.offer = offer;
  }

  public int offerPrice(int quantity) {
    return offer.applyOffer(quantity, price);
  }
}
