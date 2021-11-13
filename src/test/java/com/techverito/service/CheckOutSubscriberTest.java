package com.techverito.service;

import com.techverito.business.CheckoutData;
import com.techverito.business.Money;
import com.techverito.dao.PreferredCommunication;
import com.techverito.dao.User;
import com.techverito.stubs.NotifierStub;
import org.junit.jupiter.api.Test;

import static com.techverito.util.Currency.INR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckOutSubscriberTest {

  @Test
  void smsNotificationToBeSentPostCheckout() {

    NotifierFactory notifierFactory = mock(NotifierFactory.class);
    UserCheckOutSubscriber userCheckOutSubscriber = new UserCheckOutSubscriber(notifierFactory);
    NotifierStub smsNotifier = new NotifierStub();
    when(notifierFactory.getNotifier(any())).thenReturn(smsNotifier);
    EventData eventData = mock(EventData.class);
    CheckoutData checkoutData =
        new CheckoutData(new User("12", "ABC", PreferredCommunication.SMS), new Money(1, INR));
    when(eventData.data())
        .thenReturn(checkoutData);

    userCheckOutSubscriber.notifyEvent(eventData);

    assertTrue(smsNotifier.hasReceivedMessage("Checkout successful"));
  }
}
