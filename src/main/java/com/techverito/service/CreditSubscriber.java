package com.techverito.service;

import com.techverito.dao.Money;

public interface CreditSubscriber {

    void notifyEvent(Money money);

}
