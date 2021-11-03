package com.techverito.dao;

import com.techverito.util.Currency;

import static com.techverito.util.Currency.GBP;
import static com.techverito.util.Currency.INR;

public class Wallet {

    private final Converter USD_INR = amount -> amount * 70;
    private final Converter INR_USD = amount -> amount / 70;
    private final Converter INR_GBP = amount -> amount / 140;

    private final Currency currency;
    private double balance;

    public Wallet(Currency currency) {
        this.currency = currency;
    }

    public void credit(double amount, Currency currency) {
        if (currency.equals(this.currency)) {
            this.balance += amount;
            return;
        }
        if (this.currency.equals(GBP)) {
            this.balance += INR_GBP.convert(amount);
            return;
        }
        if (currency.equals(GBP)) {
            this.balance = 140;
            return;
        }
        double convertedAmt = this.currency.equals(INR)
                ? USD_INR.convert(amount)
                : INR_USD.convert(amount);
        this.balance += convertedAmt;
    }

    public double balance() {
        return this.balance;
    }
}
