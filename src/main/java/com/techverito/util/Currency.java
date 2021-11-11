package com.techverito.util;

public enum Currency {
  BTH(1),
  INR(BTH.conversionValue * 2),
  USD(INR.conversionValue * 70),
  GBP(INR.conversionValue * 140);

  public final double conversionValue;

  Currency(double conversionValue) {
    this.conversionValue = conversionValue;
  }

  public double convertFrom(double amount, Currency currency) {
    double baseValue = currency.convertToBase(amount);
    return convertFromBase(baseValue);
  }

  private double convertFromBase(double baseValue) {
    return baseValue / conversionValue;
  }

  private double convertToBase(double amount) {
    return conversionValue * amount;
  }
}
