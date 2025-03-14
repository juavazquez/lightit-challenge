package com.lightit.challenge.service.impl.notifications.strategy.email;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.notifications.IEmailNotificationService;
import com.lightit.challenge.service.notifications.INotificationStrategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Qualifier("email")
public class EmailNotificationStrategy implements INotificationStrategy {

    private final IEmailNotificationService emailNotificationService;

    @Override
    public void send(User user, Map<String, String> data) {
        emailNotificationService.send(user.getEmail(), data.get("subject"), data.get("message"));
    }

}
