package com.techverito.business;


import com.techverito.dao.Money;

public interface PaymentMethod {

  boolean charge(Money money);

}
