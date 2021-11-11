package com.techverito.dao;

import com.techverito.util.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void _70_INRshouldBeEqual_1_USD(){
        assertEquals(new Money(70,Currency.INR),new Money(1, Currency.USD));
    }

}