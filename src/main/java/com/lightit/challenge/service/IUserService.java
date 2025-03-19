package com.lightit.challenge.service;

import java.util.Optional;

import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.entity.User;

public interface IUserService {

    User save(UserInputDto userDto);

    void setDocumentUploadSuccessful(User user);

    Optional<User> findByEmail(String email);

}
