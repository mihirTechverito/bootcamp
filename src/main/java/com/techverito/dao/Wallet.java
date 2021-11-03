package com.techverito.dao;

import com.techverito.util.Currency;

public class Wallet {

    private final Converter USD_INR = amount -> amount * 70;

    private final Currency currency;
    private double balance;

    public Wallet(Currency currency) {
        this.currency = currency;
    }

    public void credit(double amount, Currency currency) {
        if (currency.equals(this.currency)) {
            this.balance += amount;
        } else {
            double convertedAmt = USD_INR.convert(amount);
            this.balance += convertedAmt;
        }
    }

    public double balance() {
        return this.balance;
    }
}
