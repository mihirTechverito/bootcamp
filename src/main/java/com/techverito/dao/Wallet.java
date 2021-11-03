package com.techverito.dao;

import com.techverito.util.Currency;

import static com.techverito.util.Currency.INR;

public class Wallet {

    private final Converter USD_INR = amount -> amount * 70;
    private final Converter INR_USD = amount -> amount / 70;

    private final Currency currency;
    private double balance;

    public Wallet(Currency currency) {
        this.currency = currency;
    }

    public void credit(double amount, Currency currency) {
        if (!currency.equals(this.currency)) {
            double convertedAmt = this.currency.equals(INR)
                    ? USD_INR.convert(amount)
                    : INR_USD.convert(amount);
            this.balance += convertedAmt;
        } else {
            this.balance += amount;
        }
    }

    public double balance() {
        return this.balance;
    }
}
