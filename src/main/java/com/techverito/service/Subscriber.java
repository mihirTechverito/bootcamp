package com.techverito.service;


public interface Subscriber {

    void notifyEvent(EventData<?> eventData);

}
