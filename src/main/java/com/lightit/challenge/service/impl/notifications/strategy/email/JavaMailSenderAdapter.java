package com.lightit.challenge.service.impl.notifications.strategy.email;

import com.lightit.challenge.service.notifications.IEmailNotificationAdapter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSenderAdapter implements IEmailNotificationAdapter {

  private final JavaMailSender mailSender;

  public JavaMailSenderAdapter(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void send(String email, String subject, String body) throws Exception {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(email);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
  }
}
