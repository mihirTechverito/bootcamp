package com.techverito.dao;

import com.techverito.util.Currency;

import java.util.Objects;

public class Money {
  private final double amount;
  private final Currency currency;

  public Money(double amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  Money add(Money money) {

    double totalAmount = this.amount + currency.convertFrom(money.amount, money.currency);
    return new Money(totalAmount, currency);
  }


  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Money other = (Money) o;
      double convertedAmount = this.currency.convertFrom(other.amount, other.currency);
      return Double.compare(convertedAmount, amount) == 0;
  }


    @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }
}
