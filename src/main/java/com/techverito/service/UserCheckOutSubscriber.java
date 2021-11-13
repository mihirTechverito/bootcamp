package com.techverito.service;

import com.techverito.business.CheckoutData;
import com.techverito.dao.User;

public class UserCheckOutSubscriber implements Subscriber {

  private final NotifierFactory notifierFactory;

  public UserCheckOutSubscriber(NotifierFactory notifierFactory) {
    this.notifierFactory = notifierFactory;
    EventStore.getInstance().subscribe(Event.CHECKOUT,this);
  }

  @Override
  public void notifyEvent(EventData<?> eventData) {

    CheckoutData checkoutData = (CheckoutData) eventData.data();
    User user = checkoutData.user();
    notifierFactory
        .getNotifier(user.preferredCommunication())
        .sendNotification(user.contact(), "Checkout successful");

  }
}
