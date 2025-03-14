package com.lightit.challenge.service.notifications;

public interface IEmailNotificationService {

    void send(String email, String subject, String message);

}
