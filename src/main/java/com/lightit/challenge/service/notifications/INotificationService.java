package com.lightit.challenge.service.notifications;

import java.util.Map;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;

public interface INotificationService {

    void send(User user, Map<String, String> data, NotificationStrategy strategy);

}
