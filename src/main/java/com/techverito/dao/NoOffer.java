package com.techverito.dao;

public class NoOffer implements Offer {

  @Override
  public int applyOffer(int quantity, int price) {
    return quantity * price;
  }
}
