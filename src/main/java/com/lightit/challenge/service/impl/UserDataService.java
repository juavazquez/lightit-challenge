package com.lightit.challenge.service.impl;

import com.lightit.challenge.builder.EmailBuilder;
import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.dto.UserOutputDto;
import com.lightit.challenge.entity.User;
import com.lightit.challenge.mapper.UserMapper;
import com.lightit.challenge.service.IUserDataService;
import com.lightit.challenge.service.IUserService;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;
import com.lightit.challenge.service.notifications.INotificationService;
import com.lightit.challenge.service.storage.IDocumentUploader;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
  public void register(UserInputDto userDto) {
    // Save user to database
    logger.info("Saving user {} to database", userDto.getEmail());
    User user = userService.save(userDto);

    // Save user photo to third party service
    logger.info("Uploading user document image for user {}", user.getId());
    boolean success = documentUploader.upload(userDto.getDocumentImg(), user.getId().toString());
    if (success) {
      logger.info("Document upload successful for user {} - Updating database field", user.getId());
      user = userService.setDocumentUploadSuccessful(user);
    } else {
      logger.warn("Document upload failed for user {}", user.getId());
    }

    // Send welcome email
    logger.info("Sending welcome email to user {}", user.getEmail());
    notificationService.send(
        user, EmailBuilder.buildWelcomeEmail(user.getFirstName()), NotificationStrategy.EMAIL);
  }

  @Override
  public UserOutputDto getUser(String email) {
    User user =
        userService
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    UserMapper mapper = new UserMapper();
    return new UserOutputDto(
        mapper.toDto(user), documentUploader.retrieve(user.getId().toString()));
  }
}
