package com.lightit.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.dto.UserOutputDto;
import com.lightit.challenge.entity.Address;
import com.lightit.challenge.entity.User;
import com.lightit.challenge.service.impl.UserDataService;
import com.lightit.challenge.service.impl.notifications.NotificationService.NotificationStrategy;
import com.lightit.challenge.service.notifications.INotificationService;
import com.lightit.challenge.service.storage.IDocumentUploader;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class UserDataServiceTest {

  @Mock IUserService userService;

  @Mock IDocumentUploader documentUploader;

  @Mock INotificationService notificationService;

  @InjectMocks UserDataService userDataService;

  @Test
  public void testRegister_SavesUserToDB_DocumentUploadSucceeds() {
    MultipartFile documentImg = mock(MultipartFile.class);
    UserInputDto userDto = new UserInputDto();
    userDto.setFirstName("John");
    userDto.setEmail("john.doe@example.com");
    userDto.setDocumentImg(documentImg);

    UUID userId = UUID.randomUUID();
    User savedUser = new User();
    savedUser.setId(userId);
    savedUser.setFirstName("John");
    savedUser.setEmail("john.doe@example.com");
    savedUser.setDocumentUploadSuccessful(true);

    when(userService.save(userDto)).thenReturn(savedUser);
    when(documentUploader.upload(userDto.getDocumentImg(), savedUser.getId().toString()))
        .thenReturn(true);
    when(userService.setDocumentUploadSuccessful(savedUser)).thenReturn(savedUser);

    userDataService.register(userDto);

    verify(userService).save(userDto);
    verify(userService).setDocumentUploadSuccessful(savedUser);
    verify(documentUploader).upload(userDto.getDocumentImg(), savedUser.getId().toString());
    verify(notificationService).send(eq(savedUser), anyMap(), eq(NotificationStrategy.EMAIL));
  }

  @Test
  public void testRegister_SavesUserToDB_DocumentUploadFails() {
    MultipartFile documentImg = mock(MultipartFile.class);
    UserInputDto userDto = new UserInputDto();
    userDto.setFirstName("John");
    userDto.setEmail("john.doe@example.com");
    userDto.setDocumentImg(documentImg);

    UUID userId = UUID.randomUUID();
    User savedUser = new User();
    savedUser.setId(userId);
    savedUser.setFirstName("John");
    savedUser.setEmail("john.doe@example.com");
    savedUser.setDocumentUploadSuccessful(false);

    when(userService.save(userDto)).thenReturn(savedUser);
    when(documentUploader.upload(userDto.getDocumentImg(), savedUser.getId().toString()))
        .thenReturn(false);

    userDataService.register(userDto);

    verify(userService).save(userDto);
    verify(userService, times(0)).setDocumentUploadSuccessful(savedUser);
    verify(documentUploader).upload(userDto.getDocumentImg(), savedUser.getId().toString());
    verify(notificationService).send(eq(savedUser), anyMap(), eq(NotificationStrategy.EMAIL));
  }

  @Test
  public void testGetUser_Succeeds() {
    String urlToDocument = "url/to/document";
    String email = "example@email.com";
    UUID userId = UUID.randomUUID();
    User user = new User();
    user.setId(userId);
    user.setFirstName("John");
    user.setEmail(email);
    user.setAddress(new Address());

    UserOutputDto expectedResult = new UserOutputDto();
    expectedResult.setEmail(email);
    expectedResult.setDocumentImgUrl(urlToDocument);
    expectedResult.setFirstName("John");

    when(userService.findByEmail(email)).thenReturn(Optional.of(user));
    when(documentUploader.retrieve(userId.toString())).thenReturn(urlToDocument);

    UserOutputDto userOutputDto = userDataService.getUser(email);

    verify(userService).findByEmail(email);
    verify(documentUploader).retrieve(userId.toString());
    assertEquals(expectedResult, userOutputDto);
  }

  @Test
  public void testGetUser_NotFound() {
    when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> userDataService.getUser("example@email.com"));
  }
}
