package com.lightit.challenge.service.impl.notifications.strategy.sms;

import java.util.Map;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.notifications.INotificationStrategy;
import com.lightit.challenge.service.notifications.ISmsNotificationAdapter;

public class SmsNotificationStrategy implements INotificationStrategy {

    // private final ISmsNotificationAdapter smsNotificationAdapter;

    @Override
    public void send(User user, Map<String, String> data) {
        throw new UnsupportedOperationException("Unimplemented method 'send' for SmsNotificationStrategy");
    }

}
