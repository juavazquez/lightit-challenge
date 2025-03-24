package com.lightit.challenge.service;

import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.dto.UserOutputDto;

public interface IUserDataService {

  void register(UserInputDto userDto);

  UserOutputDto getUser(String email);
}
