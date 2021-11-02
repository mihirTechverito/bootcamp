package com.techverito.dao;

public interface Offer {

  Offer None = new NoOffer();

  int applyOffer(int quantity, int price);
}
