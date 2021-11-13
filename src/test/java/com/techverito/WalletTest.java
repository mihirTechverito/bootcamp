package com.techverito;

import com.techverito.dao.Money;
import com.techverito.dao.Wallet;
import com.techverito.exception.BalanceInsufficientException;
import com.techverito.service.Event;
import com.techverito.service.EventStore;
import com.techverito.stubs.SubscriberStub;
import org.junit.jupiter.api.Test;

import static com.techverito.util.Currency.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WalletTest {

  @Test
  void add1INRToEmptyWalletTotalBalanceBecomes1INR() {
    Wallet wallet = new Wallet(INR);
    Money money = new Money(1, INR);
    wallet.credit(money);

    assertEquals(money, wallet.balance());
  }

  @Test
  void addDecimalINRToEmptyWalletTotalBalanceBecomes1_5INR() {
    Wallet wallet = new Wallet(INR);
    Money money = new Money(1.5, INR);
    wallet.credit(money);

    assertEquals(money, wallet.balance());
  }

  @Test
  void add_2_INRtoWalletHaving_1_INRbalanceBecomes_3_INR() {
    Wallet wallet = new Wallet(INR);
    wallet.credit(new Money(1, INR));
    wallet.credit(new Money(2, INR));
    Money expected = new Money(3, INR);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_1_USDToEmptyUSDWalletTotalBalanceBecomes_1_USD() {
    Wallet wallet = new Wallet(USD);
    wallet.credit(new Money(1, USD));
    Money expected = new Money(1, USD);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_1_USDToEmptyINRWalletTotalBalanceBecomes_70_INR() {
    Wallet wallet = new Wallet(INR);
    wallet.credit(new Money(1, USD));
    Money expected = new Money(70, INR);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_2_USDToINRWalletWith10INRTotalBalanceBecomes_150_INR() {
    Wallet wallet = new Wallet(INR);

    wallet.credit(new Money(10, INR));
    wallet.credit(new Money(2, USD));
    Money expected = new Money(150, INR);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_70_INRToEmptyUSDWalletTotalBalanceBecomes_1_USD() {
    Wallet wallet = new Wallet(USD);

    wallet.credit(new Money(70, INR));

    Money expected = new Money(1, USD);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_210_INRToUSDWalletWith_1_USDTotalBalanceBecomes_4_USD() {
    Wallet wallet = new Wallet(USD);

    wallet.credit(new Money(1, USD));
    wallet.credit(new Money(210, INR));

    Money expected = new Money(4, USD);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_140_INRtoEmptyGBPWalletTotalBalance_1_GBP() {
    Wallet wallet = new Wallet(GBP);
    wallet.credit(new Money(140, INR));
    Money expected = new Money(1, GBP);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_280_INRtoEmptyGBPWalletTotalBalance_2_GBP() {
    Wallet wallet = new Wallet(GBP);
    wallet.credit(new Money(280, INR));
    Money expected = new Money(2, GBP);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_2_USDtoEmptyGBPWalletTotalBalance_1_GBP() {
    Wallet wallet = new Wallet(GBP);
    wallet.credit(new Money(2, USD));
    Money expected = new Money(1, GBP);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_140_INRTwiceToEmptyGBPWalletTotalBalanceBecomes_2_GBP() {
    Wallet wallet = new Wallet(GBP);
    wallet.credit(new Money(140, INR));
    wallet.credit(new Money(140, INR));
    Money expected = new Money(2, GBP);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void add_1_GBPToEmptyINRWalletTotalBalanceBecomes_140_INR() {
    Wallet wallet = new Wallet(INR);
    wallet.credit(new Money(1, GBP));
    Money expected = new Money(140, INR);
    assertEquals(expected, wallet.balance());
  }

  @Test
  void _1_GBPTwiceToINRWalletTotalBalanceBecomes_280_INR() {
    Wallet wallet = new Wallet(INR);
    wallet.credit(new Money(1, GBP));
    wallet.credit(new Money(1, GBP));
    Money expected = new Money(280, INR);
    assertEquals(expected, wallet.balance());
  }


  @Test
  void sendCreditNotificationToSubscriber() {

    EventStore eventStore = EventStore.getInstance();
    Wallet wallet = new Wallet(INR);
    Money money = new Money(2, INR);
    SubscriberStub auditorStub = new SubscriberStub(eventStore, Event.CREDIT);
    SubscriberStub accountStub = new SubscriberStub(eventStore, Event.CREDIT);
    wallet.credit(money);


    assertEquals(money, auditorStub.eventData().data());
    assertEquals(money, accountStub.eventData().data());
  }


  @Test
  void deduct_1INR_FromINRWalletWith_10INR_TotalBalanceBecomes_9INR() {
    Wallet wallet = new Wallet(INR);
    wallet.credit(new Money(10, INR));
    Money expectedBalance = new Money(9,INR);
    wallet.charge(new Money(1,INR));
    Money actualBalance = wallet.balance();
    assertEquals(expectedBalance,actualBalance);
  }

  @Test
  void deduct_11INR_FromINRWalletWith_10INR_ThrowExceptionBalanceInsufficient() {
    Wallet wallet = new Wallet(INR);
    wallet.credit(new Money(10, INR));

    assertThrows(BalanceInsufficientException.class,() -> wallet.charge(new Money(11,INR)));
  }

  @Test
  void deduct_140INR_FromUSDWalletWith_4USD_TotalBalanceBecomes_2USD() {
    Wallet wallet = new Wallet(USD);
    wallet.credit(new Money(4, USD));
    Money expectedBalance = new Money(2,USD);
    wallet.charge(new Money(140,INR));
    Money actualBalance = wallet.balance();
    assertEquals(expectedBalance,actualBalance);

  }
}
