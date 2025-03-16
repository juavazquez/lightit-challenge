package com.lightit.challenge.service.impl;

import org.springframework.stereotype.Service;

import com.lightit.challenge.builder.EmailBuilder;
import com.lightit.challenge.dto.UserDto;
import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.IUserDataService;
import com.lightit.challenge.service.IUserService;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;
import com.lightit.challenge.service.notifications.INotificationService;
import com.lightit.challenge.service.storage.IDocumentUploader;

@Service
public class UserDataService implements IUserDataService {

    private final IUserService userService;
    private final INotificationService notificationService;
    private final IDocumentUploader documentUploader;

    public UserDataService(
            IUserService userService,
            INotificationService notificationService,
            IDocumentUploader documentUploader) {
        this.userService = userService;
        this.notificationService = notificationService;
        this.documentUploader = documentUploader;
    }

    @Override
    public void register(UserDto userDto) {
        // Save user to database
        User user = userService.save(userDto);

        // Save user photo to third party service
        boolean success = documentUploader.upload(userDto.getDocumentImg(), user.getId().toString());
        if (success) {
            userService.setDocumentUploadSuccessful(user);
        }

        // Send welcome email
        notificationService.send(
                user,
                EmailBuilder.buildWelcomeEmail(user.getFirstName()),
                NotificationStrategy.EMAIL);
    }

}
