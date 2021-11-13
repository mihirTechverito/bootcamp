package com.techverito.dao;

import com.techverito.util.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

  @Test
  void _70INR_shouldBeEqual_1USD() {
    assertEquals(new Money(70, Currency.INR), new Money(1, Currency.USD));
  }
}
