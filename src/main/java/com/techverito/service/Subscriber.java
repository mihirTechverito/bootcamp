package com.techverito.service;


public interface Subscriber<T> {

    void notifyEvent(EventData<?> eventData);

    void notifyEventV2(T eventData);




}
