package com.lightit.challenge.service.impl.notifications;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.notifications.INotificationService;
import com.lightit.challenge.service.notifications.INotificationStrategy;

@Service
public class NotificationService implements INotificationService {

    // Create a map of INotificationStrategy objects with the key being
    // the value of the Qualifier annotation of the class
    private final Map<String, INotificationStrategy> strategies;

    public NotificationService(List<INotificationStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(
                        Collectors.toMap(
                                strategy -> strategy.getClass().getAnnotation(Qualifier.class).value(),
                                strategy -> strategy));
    }

    @Override
    public void send(User user, Map<String, String> data, NotificationStrategy strategy) {
        INotificationStrategy notificationStrategy = getStrategy(strategy);
        notificationStrategy.send(user, data);
    }

    private INotificationStrategy getStrategy(NotificationStrategy strategy) {
        return Optional.ofNullable(strategy)
                .map(s -> strategies.get(s.name()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid strategy"));
    }

    public enum NotificationStrategy {
        EMAIL
        // SMS
    }
}
