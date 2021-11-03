package com.techverito.dao;

import com.techverito.util.Currency;

public class Wallet {

  private final Currency currency;
  private double balance;

  public Wallet(Currency currency) {
    this.currency = currency;
  }

  public double balance() {
    return this.balance;
  }

  public void credit(double creditAmount, Currency creditCurrency) {
    this.balance += (creditAmount * creditCurrency.conversionValue) / this.currency.conversionValue;
  }
}
