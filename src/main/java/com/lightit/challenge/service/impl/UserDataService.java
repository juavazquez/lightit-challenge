package com.lightit.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserDataService.class);

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
        logger.info("Saving user {} to database", userDto.getEmail());
        User user = userService.save(userDto);

        // Save user photo to third party service
        logger.info("Uploading user document image for user {}", user.getId());
        boolean success = documentUploader.upload(userDto.getDocumentImg(), user.getId().toString());
        if (success) {
            logger.info("Document upload successful for user {} - Updating database field", user.getId());
            userService.setDocumentUploadSuccessful(user);
        }
        // If document upload fails, log the error and continue (can ask for document
        // later)

        // Send welcome email
        logger.info("Sending welcome email to user {}", user.getEmail());
        notificationService.send(
                user,
                EmailBuilder.buildWelcomeEmail(user.getFirstName()),
                NotificationStrategy.EMAIL);
    }

}
