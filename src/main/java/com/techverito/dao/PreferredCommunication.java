package com.techverito.dao;

import com.techverito.service.EmailNotifier;
import com.techverito.service.Notifier;
import com.techverito.service.SmsNotifier;

public enum PreferredCommunication {
  SMS(new SmsNotifier()),
  EMAIL(new EmailNotifier());

  private final Notifier notifier;

  PreferredCommunication(Notifier notifier) {
    this.notifier = notifier;
  }

  public Notifier notifier(){
    return notifier;
  }
}
