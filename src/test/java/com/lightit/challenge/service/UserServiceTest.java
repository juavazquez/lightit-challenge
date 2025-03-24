package com.lightit.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.entity.User;
import com.lightit.challenge.repository.UserRepository;
import com.lightit.challenge.service.impl.UserService;
import jakarta.persistence.EntityExistsException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock UserRepository userRepository;

  @InjectMocks UserService userService;

  @Test
  void testSave_Succeeds() {
    UserInputDto userInputDto = new UserInputDto();
    userInputDto.setEmail("test@example.com");
    userInputDto.setFirstName("John");
    userInputDto.setLastName("Doe");
    userInputDto.setPhoneNumber("1234567890");
    userInputDto.setStreetLine("123 Main St");
    userInputDto.setCity("Anytown");
    userInputDto.setState("Anystate");
    userInputDto.setCountry("Anycountry");

    User user = new User();
    user.setEmail("test@example.com");

    when(userRepository.findByEmail(userInputDto.getEmail())).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    User savedUser = userService.save(userInputDto);

    assertEquals(userInputDto.getEmail(), savedUser.getEmail());
  }

  @Test
  void testSave_UserAlreadyExists() {
    UserInputDto userInputDto = new UserInputDto();
    userInputDto.setEmail("test@example.com");

    User existingUser = new User();
    existingUser.setEmail("test@example.com");

    when(userRepository.findByEmail(userInputDto.getEmail())).thenReturn(Optional.of(existingUser));

    assertThrows(EntityExistsException.class, () -> userService.save(userInputDto));
  }

  @Test
  void testFindByEmail() {
    String email = "test@example.com";
    User user = new User();
    user.setEmail(email);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    Optional<User> foundUser = userService.findByEmail(email);

    assertEquals(email, foundUser.get().getEmail());
  }

  @Test
  void testSetDocumentUploadSuccessful() {

    User user = new User();
    user.setDocumentUploadSuccessful(false);

    when(userRepository.save(user)).thenReturn(user);

    User updatedUser = userService.setDocumentUploadSuccessful(user);

    assertEquals(true, updatedUser.getDocumentUploadSuccessful());
  }
}
