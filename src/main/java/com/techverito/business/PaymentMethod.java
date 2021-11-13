package com.techverito.business;


public interface PaymentMethod {

  boolean charge(Money money);

}
