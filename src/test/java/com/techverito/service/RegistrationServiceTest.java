package com.techverito.service;

import com.techverito.dao.PreferredCommunication;
import com.techverito.dao.User;
import com.techverito.stubs.NotifierStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RegistrationServiceTest {

  @Test
  @DisplayName("When user registers with SMS preference, user receives welcome message over an SMS")
  void whenUserRegistersWithSmsPreferenceWelcomeSmsSentToPhoneNumber() {

    // Arrange - Given
    NotifierStub smsNotifier = new NotifierStub();
    NotifierFactory notifierFactory = mock(NotifierFactory.class);
    when(notifierFactory.getNotifier(PreferredCommunication.SMS)).thenReturn(smsNotifier);
    RegistrationService registrationService = new RegistrationService(notifierFactory);
    User user = new User("1234567890", "john@example.com", PreferredCommunication.SMS);

    // Act - When
    registrationService.register(user);

    // Assert - Then
    Assertions.assertTrue(smsNotifier.hasReceivedMessage("Thank you for registration"));
    Assertions.assertTrue(smsNotifier.hasRecipient("1234567890"));
  }

  @Test
  @DisplayName(
      "When user registers with EMAIL preference, user receives welcome message over an EMAIL")
  void whenUserRegistersWithEmailPreferenceWelcomeMessageSentToEmail() {

    // Arrange - Given
    NotifierStub emailNotifier = new NotifierStub();
    NotifierFactory notifierFactory = mock(NotifierFactory.class);
    when(notifierFactory.getNotifier(PreferredCommunication.EMAIL)).thenReturn(emailNotifier);

    RegistrationService registrationService = new RegistrationService(notifierFactory);
    User user = new User("1234567890", "john@example.com", PreferredCommunication.EMAIL);

    // Act - When
    registrationService.register(user);

    // Assert - Then
    Assertions.assertTrue(emailNotifier.hasReceivedMessage("Thank you for registration"));
    Assertions.assertTrue(emailNotifier.hasRecipient("john@example.com"));
  }

  @Test
  void whenUserRegistersWelcomeEmailSentOnce() {
    Notifier emailNotifier = mock(Notifier.class);
    NotifierFactory notifierFactory = mock(NotifierFactory.class);
    when(notifierFactory.getNotifier(PreferredCommunication.EMAIL)).thenReturn(emailNotifier);

    RegistrationService registrationService = new RegistrationService(notifierFactory);

    User user = new User("1234567890", "john@example.com", PreferredCommunication.EMAIL);

    registrationService.register(user);

    verify(emailNotifier, times(1))
        .sendNotification("john@example.com", "Thank you for registration");
  }

  @Test
  void whenUserRegistersWelcomeSmsSentOnce() {
    Notifier smsNotifier = mock(Notifier.class);
    NotifierFactory notifierFactory = mock(NotifierFactory.class);
    User user = new User("1234567890", "john@example.com", PreferredCommunication.SMS);

    when(notifierFactory.getNotifier(PreferredCommunication.SMS)).thenReturn(smsNotifier);
    RegistrationService registrationService = new RegistrationService(notifierFactory);

    // arrange
    // act
    // assert

    registrationService.register(user);

    verify(smsNotifier, times(1))
        .sendNotification("1234567890", "Thank you for registration");
  }
}
