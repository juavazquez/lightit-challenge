package com.lightit.challenge.service.impl.notifications.strategy.email;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;
import com.lightit.challenge.service.notifications.IEmailNotificationAdapter;
import com.lightit.challenge.service.notifications.INotificationStrategy;

@Service
public class EmailNotificationStrategy implements INotificationStrategy {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationStrategy.class);
    private final IEmailNotificationAdapter emailNotificationAdapter;

    public EmailNotificationStrategy(IEmailNotificationAdapter emailNotificationAdapter) {
        this.emailNotificationAdapter = emailNotificationAdapter;
    }

    @Override
    public NotificationStrategy getStrategy() {
        return NotificationStrategy.EMAIL;
    }

    @Override
    @Async // Handle the sending of the email notification asynchronously
    public void send(User user, Map<String, String> data) {
        logger.info("Sending email notification to user {}", user.getEmail());
        try {
            emailNotificationAdapter.send(user.getEmail(), data.get("subject"), data.get("body"));
        } catch (Exception e) {
            logger.error("Error sending email notification", e);
        }
        logger.info("Email notification sent successfully to user {}", user.getEmail());
    }

}
