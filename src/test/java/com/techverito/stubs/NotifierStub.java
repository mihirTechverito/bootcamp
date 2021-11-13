package com.techverito.stubs;

import com.techverito.service.Notifier;

public class NotifierStub implements Notifier {
    private String recipient;
    private String message;

    public boolean hasReceivedMessage(String message) {
        return message.equals(this.message);
    }

    public boolean hasRecipient(String recipient) {
        return recipient.equals(this.recipient);
    }

    @Override
    public void sendNotification(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }
}
