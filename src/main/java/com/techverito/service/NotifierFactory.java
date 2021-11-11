package com.techverito.service;

import com.techverito.dao.PreferredCommunication;

import java.util.EnumMap;
import java.util.Map;

public class NotifierFactory {

    private static final Map<PreferredCommunication,Notifier> notifierMap = new EnumMap<>(PreferredCommunication.class);

    static {
        notifierMap.put(PreferredCommunication.EMAIL,new EmailNotifier());
        notifierMap.put(PreferredCommunication.SMS,new SmsNotifier());
    }

    public Notifier getNotifier(PreferredCommunication preferredCommunication) {

        return notifierMap.get(preferredCommunication);
    }
}
