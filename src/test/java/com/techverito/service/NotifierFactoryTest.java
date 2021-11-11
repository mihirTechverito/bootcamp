package com.techverito.service;

import com.techverito.dao.PreferredCommunication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotifierFactoryTest {

  @Test
  void factoryGivesSmsNotifierForPreferredCommunicationSms() {
      NotifierFactory notifierFactory = new NotifierFactory();
      Notifier notifier = notifierFactory.getNotifier(PreferredCommunication.SMS);
      assertEquals(SmsNotifier.class,notifier.getClass());
  }

    @Test
    void factoryGivesEmailNotifierForPreferredCommunicationEmail() {
        NotifierFactory notifierFactory = new NotifierFactory();
        Notifier notifier = notifierFactory.getNotifier(PreferredCommunication.EMAIL);
        assertEquals(EmailNotifier.class,notifier.getClass());
    }
}