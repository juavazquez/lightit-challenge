package com.lightit.challenge.service.notifications;

public interface ISmsNotificationAdapter {

    void send(String phone, String message) throws Exception;

}
