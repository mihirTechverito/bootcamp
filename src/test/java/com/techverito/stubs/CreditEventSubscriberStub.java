package com.techverito.stubs;

import com.techverito.business.Money;
import com.techverito.service.Event;
import com.techverito.service.EventData;
import com.techverito.service.EventStore;
import com.techverito.service.Subscriber;

public class CreditEventSubscriberStub implements Subscriber<Money> {
    private EventData<?> data;

    public CreditEventSubscriberStub(EventStore eventStore, Event event) {
        eventStore.subscribe(event, this);
    }

    public EventData<?> eventData() {
        return data;
    }

    @Override
    public void notifyEvent(EventData<?> eventData) {
        this.data = eventData;
    }

    @Override
    public void notifyEventV2(Money eventData) {

    }

}
