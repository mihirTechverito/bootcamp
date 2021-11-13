package com.techverito.service;

import com.techverito.dao.User;

public class CheckOutSubscriber implements Subscriber {

  @Override
  public void notifyEvent(EventData<?> eventData) {

    NotifierFactory notifierFactory = new NotifierFactory();
    User user = (User) eventData.data();
    notifierFactory
        .getNotifier(user.preferedCommunication())
        .sendNotification(user.contact(), "Order placed");
  }
}
