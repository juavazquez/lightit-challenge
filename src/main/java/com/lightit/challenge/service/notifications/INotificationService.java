package com.lightit.challenge.service.notifications;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;
import java.util.Map;

public interface INotificationService {

  void send(User user, Map<String, String> data, NotificationStrategy strategy);
}
