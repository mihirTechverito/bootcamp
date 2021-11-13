package com.techverito.service;

public class EventData<T> {

    private final T data;

    public EventData(T data) {
        this.data = data;
    }

    public T data() {
        return data;
    }
}
