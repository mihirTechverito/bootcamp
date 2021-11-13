package com.techverito.stubs;

import com.techverito.service.Event;
import com.techverito.service.EventData;
import com.techverito.service.EventStore;
import com.techverito.service.Subscriber;

public class SubscriberStub implements Subscriber {
    private EventData<?> data;

    public SubscriberStub(EventStore eventStore,Event event) {
        eventStore.subscribe(event, this);
    }

    public EventData<?> eventData() {
        return data;
    }

    @Override
    public void notifyEvent(EventData<?> eventData) {
        this.data = eventData;
    }
}
