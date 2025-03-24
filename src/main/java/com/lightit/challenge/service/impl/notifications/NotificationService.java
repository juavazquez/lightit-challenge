package com.lightit.challenge.service.impl.notifications;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.notifications.INotificationService;
import com.lightit.challenge.service.notifications.INotificationStrategy;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService {

  private final List<INotificationStrategy> strategies;

  public NotificationService(List<INotificationStrategy> strategies) {
    this.strategies = strategies;
  }

  @Override
  public void send(User user, Map<String, String> data, NotificationStrategy strategy) {
    INotificationStrategy notificationStrategy = getStrategy(strategy);
    notificationStrategy.send(user, data);
  }

  private INotificationStrategy getStrategy(NotificationStrategy strategy) {
    return strategies.stream()
        .filter(s -> s.getStrategy() == strategy)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid notification strategy"));
  }

  public enum NotificationStrategy {
    EMAIL
    // SMS
  }
}
