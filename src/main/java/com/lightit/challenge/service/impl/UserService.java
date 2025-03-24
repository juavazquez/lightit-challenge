package com.lightit.challenge.service.impl;

import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.entity.User;
import com.lightit.challenge.mapper.UserMapper;
import com.lightit.challenge.repository.UserRepository;
import com.lightit.challenge.service.IUserService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public User save(UserInputDto userDto) {
    // Check if user already exists
    userRepository
        .findByEmail(userDto.getEmail())
        .ifPresent(
            user -> {
              throw new EntityExistsException("User already registered");
            });

    // Save user to database
    UserMapper userMapper = new UserMapper();
    User user = userRepository.save(userMapper.toEntity(userDto));

    return user;
  }

  @Override
  public User setDocumentUploadSuccessful(User user) {
    user.setDocumentUploadSuccessful(true);
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
