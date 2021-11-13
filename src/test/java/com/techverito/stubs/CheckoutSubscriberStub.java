package com.techverito.stubs;

import com.techverito.business.CheckoutData;
import com.techverito.service.Event;
import com.techverito.service.EventData;
import com.techverito.service.EventStore;
import com.techverito.service.Subscriber;

public class CheckoutSubscriberStub implements Subscriber<CheckoutData> {
    private CheckoutData data;

    public CheckoutSubscriberStub(EventStore eventStore, Event event) {
        eventStore.subscribe(event, this);
    }

    public CheckoutData eventData() {
        return data;
    }

    @Override
    public void notifyEvent(EventData<?> eventData) {
    }

    @Override
    public void notifyEventV2(CheckoutData eventData) {
        this.data = eventData;

    }
}
