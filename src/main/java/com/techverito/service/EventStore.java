package com.techverito.service;

import com.techverito.dao.Money;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class EventStore {

  private static final EnumMap<Event, List<CreditSubscriber>> subscribersMap =
      new EnumMap<>(Event.class);
  private static EventStore instance;

  private EventStore() {}

  public static EventStore getInstance() {
    if (instance == null) instance = new EventStore();

    return instance;
  }

  public void publishEvent(Event event, Money money) {

    List<CreditSubscriber> subs = subscribersMap.get(event);

    if (subs != null) subs.forEach(s -> s.notifyEvent(money));
  }

  public void subscribe(Event event, CreditSubscriber creditSubscriber) {
    List<CreditSubscriber> subscribers = subscribersMap.get(event);
    if (subscribers != null) {
      subscribers.add(creditSubscriber);
    } else {
      List<CreditSubscriber> newSubscribers = new ArrayList<>();
      newSubscribers.add(creditSubscriber);
      subscribersMap.put(event, newSubscribers);
    }
  }
}
