package com.techverito.dao;

import com.techverito.business.Money;
import com.techverito.business.PaymentMethod;
import com.techverito.exception.BalanceInsufficientException;
import com.techverito.service.Event;
import com.techverito.service.EventData;
import com.techverito.service.EventStore;
import com.techverito.util.Currency;

public class Wallet implements PaymentMethod {

  private Money money;

  public Wallet(Currency currency) {
    this.money = new Money(0, currency);
  }

  public Money balance() {
    return this.money;
  }

  public void credit(Money creditMoney) {
    this.money = this.money.add(creditMoney);
    EventStore.getInstance().publishEvent(Event.CREDIT, new EventData<>(creditMoney));
  }

  @Override
  public boolean charge(Money debitMoney) {
    return debit(debitMoney);
  }

  private boolean debit(Money debitMoney) {
    try {
      this.money = this.money.deduct(debitMoney);
      return true;
    } catch (Exception e) {
      throw new BalanceInsufficientException();
    }
  }
}
