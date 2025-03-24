package com.lightit.challenge.service.notifications;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.impl.notifications.NotificationService;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotificationsServiceTest {

  @Mock INotificationStrategy emailNotificationStrategy;

  NotificationService notificationService;

  @BeforeEach
  void setup() {
    List<INotificationStrategy> strategies = List.of(emailNotificationStrategy);
    notificationService = new NotificationService(strategies);
  }

  @Test
  public void testSendNotificationWithEmailStrategy() {
    User user = new User();
    Map<String, String> data = new HashMap<>();
    data.put("subject", "Test");
    data.put("body", "Test body");

    when(emailNotificationStrategy.getStrategy()).thenReturn(NotificationStrategy.EMAIL);

    notificationService.send(user, data, NotificationStrategy.EMAIL);

    verify(emailNotificationStrategy).send(user, data);
  }
}
