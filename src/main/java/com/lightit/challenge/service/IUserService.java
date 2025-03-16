package com.lightit.challenge.service;

import com.lightit.challenge.dto.UserDto;
import com.lightit.challenge.entity.User;

public interface IUserService {

    User save(UserDto userDto);

    void setDocumentUploadSuccessful(User user);

}
