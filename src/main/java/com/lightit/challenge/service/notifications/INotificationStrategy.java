package com.lightit.challenge.service.notifications;

import java.util.Map;

import com.lightit.challenge.entity.User;

public interface INotificationStrategy {

    void send(User user, Map<String, String> data);
    
}
