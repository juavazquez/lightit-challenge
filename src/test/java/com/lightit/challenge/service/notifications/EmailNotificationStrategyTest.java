package com.lightit.challenge.service.notifications;

import static org.mockito.Mockito.verify;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.impl.notifications.strategy.email.EmailNotificationStrategy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmailNotificationStrategyTest {

  @Mock IEmailNotificationAdapter emailNotificationAdapter;

  @InjectMocks EmailNotificationStrategy emailNotificationStrategy;

  private final String email = "example@email.com";
  private final String subject = "Test subject";
  private final String body = "Test body";

  @Test
  public void testSendEmail() throws Exception {
    User user = new User();
    user.setEmail(email);
    Map<String, String> data = Map.of("subject", subject, "body", body);

    emailNotificationStrategy.send(user, data);

    verify(emailNotificationAdapter).send(email, subject, body);
  }
}
