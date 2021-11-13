package com.techverito.business;

import com.techverito.dao.User;

public class CheckoutData {
    private User user;
    private Money checkoutMoney;

    public CheckoutData(User user, Money checkoutMoney) {

        this.user = user;
        this.checkoutMoney = checkoutMoney;
    }


    public User user() {
        return user;
    }

    public Money spent() {
        return checkoutMoney;
    }
}
