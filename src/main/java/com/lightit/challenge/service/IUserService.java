package com.lightit.challenge.service;

import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.entity.User;
import java.util.Optional;

public interface IUserService {

  User save(UserInputDto userDto);

  User setDocumentUploadSuccessful(User user);

  Optional<User> findByEmail(String email);
}
