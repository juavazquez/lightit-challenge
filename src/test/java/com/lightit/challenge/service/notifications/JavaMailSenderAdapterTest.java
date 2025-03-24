package com.lightit.challenge.service.notifications;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lightit.challenge.service.impl.notifications.strategy.email.JavaMailSenderAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class JavaMailSenderAdapterTest {

  @Mock JavaMailSender mailSender;

  @InjectMocks JavaMailSenderAdapter javaMailSenderAdapter;

  @Test
  public void testSendEmail() throws Exception {
    String email = "test@example.com";
    String subject = "Test Subject";
    String body = "Test Body";

    javaMailSenderAdapter.send(email, subject, body);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject(subject);
    message.setText(body);

    verify(mailSender, times(1)).send(message);
  }
}
