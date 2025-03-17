package com.lightit.challenge.service.notifications;

import java.util.Map;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;

public interface INotificationStrategy {

    NotificationStrategy getStrategy();

    void send(User user, Map<String, String> data);

}
