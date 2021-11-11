package com.techverito.dao;

public class User {

  private final String mobileNumber;
  private final String email;
  private final PreferredCommunication preferredCommunication;

  public User(String mobileNumber, String email, PreferredCommunication preferredCommunication) {
    this.mobileNumber = mobileNumber;
    this.email = email;
    this.preferredCommunication = preferredCommunication;
  }

  public String contact() {
    if (this.preferredCommunication.equals(PreferredCommunication.SMS)) {
      return mobileNumber;
    } else {
      return email;
    }
  }

  public PreferredCommunication preferedCommunication() {
    return preferredCommunication;
  }
}
