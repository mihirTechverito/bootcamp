package com.techverito.service;

import com.techverito.dao.Money;

public interface Subscriber {

    void notifyEvent(EventData<?> eventData);

}
