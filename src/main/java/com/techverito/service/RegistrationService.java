package com.techverito.service;

import com.techverito.dao.User;

public class RegistrationService {

  private final NotifierFactory notifierFactory;

  public RegistrationService(NotifierFactory notifierFactory) {

    this.notifierFactory = notifierFactory;
  }

  public void register(User newUser) {

    this.notifierFactory
        .getNotifier(newUser.preferedCommunication())
        .sendNotification(newUser.contact(), "Thank you for registration");
  }
}
