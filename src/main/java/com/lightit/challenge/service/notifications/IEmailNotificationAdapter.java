package com.lightit.challenge.service.notifications;

public interface IEmailNotificationAdapter {

    void send(String email, String subject, String body) throws Exception;

}
