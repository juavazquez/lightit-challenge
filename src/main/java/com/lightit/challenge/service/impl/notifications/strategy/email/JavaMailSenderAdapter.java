package com.lightit.challenge.service.impl.notifications.strategy.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lightit.challenge.service.notifications.IEmailNotificationAdapter;

import jakarta.mail.internet.MimeMessage;

@Service
public class JavaMailSenderAdapter implements IEmailNotificationAdapter {

    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender mailSender;

    public JavaMailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String email, String subject, String body) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom(sender);

        mailSender.send(message);
    }

}
