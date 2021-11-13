package com.techverito.service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class EventStore {

  private static final EnumMap<Event, List<Subscriber>> subscribersMap = new EnumMap<>(Event.class);
  private static EventStore instance;

  private EventStore() {}

  public static EventStore getInstance() {
    if (instance == null) instance = new EventStore();
    return instance;
  }

  public void publishEvent(Event event, EventData<?> eventData) {
    List<Subscriber> subs = subscribersMap.get(event);
    if (subs != null) subs.forEach(s -> s.notifyEvent(eventData));
  }

  public void subscribe(Event event, Subscriber subscriber) {
    List<Subscriber> subscribers = subscribersMap.get(event);
    if (subscribers != null) {
      subscribers.add(subscriber);
    } else {
      List<Subscriber> newSubscribers = new ArrayList<>();
      newSubscribers.add(subscriber);
      subscribersMap.put(event, newSubscribers);
    }
  }
}
