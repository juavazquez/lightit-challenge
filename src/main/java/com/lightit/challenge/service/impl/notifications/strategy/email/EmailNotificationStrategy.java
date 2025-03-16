package com.lightit.challenge.service.impl.notifications.strategy.email;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.notifications.IEmailNotificationAdapter;
import com.lightit.challenge.service.notifications.INotificationStrategy;

@Component
@Qualifier("EMAIL")
public class EmailNotificationStrategy implements INotificationStrategy {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationStrategy.class);
    private final IEmailNotificationAdapter emailNotificationAdapter;

    public EmailNotificationStrategy(IEmailNotificationAdapter emailNotificationAdapter) {
        this.emailNotificationAdapter = emailNotificationAdapter;
    }

    @Override
    @Async // Handle the sending of the email notification asynchronously
    public void send(User user, Map<String, String> data) {
        try {
            emailNotificationAdapter.send(user.getEmail(), data.get("subject"), data.get("body"));
        } catch (Exception e) {
            logger.error("Error sending email notification", e);
        }
    }

}
