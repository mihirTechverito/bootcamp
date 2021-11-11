package com.techverito.dao;

import com.techverito.service.Event;
import com.techverito.service.EventStore;
import com.techverito.util.Currency;

public class Wallet {

  private Money money;

  public Wallet(Currency currency) {
    this.money = new Money(0, currency);
  }

  public Money balance() {
    return this.money;
  }

  public void credit(Money money) {
    this.money = this.money.add(money);
    EventStore.getInstance().publishEvent(Event.CREDIT, money);
  }
}
