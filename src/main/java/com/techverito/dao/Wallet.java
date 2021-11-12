package com.techverito.dao;

import com.techverito.business.PaymentOption;
import com.techverito.exception.BalanceInsufficientException;
import com.techverito.service.Event;
import com.techverito.service.EventStore;
import com.techverito.util.Currency;

public class Wallet implements PaymentOption {

  private Money money;

  public Wallet(Currency currency) {
    this.money = new Money(0, currency);
  }

  public Money balance() {
    return this.money;
  }

  public void credit(Money creditMoney) {
    this.money = this.money.add(creditMoney);
    EventStore.getInstance().publishEvent(Event.CREDIT, creditMoney);
  }

  @Override
  public boolean debit(Money debitMoney) {
    if(this.money.compareTo(debitMoney) >= 0){
      this.money = this.money.deduct(debitMoney);
      return true;
    }else{
      throw new BalanceInsufficientException();
    }
  }
}
