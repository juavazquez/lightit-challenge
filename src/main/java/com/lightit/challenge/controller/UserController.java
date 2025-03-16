package com.lightit.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lightit.challenge.controller.response.ResponseGenerator;
import com.lightit.challenge.dto.UserDto;
import com.lightit.challenge.service.IUserDataService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    private final IUserDataService userDataService;

    public UserController(IUserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> postMethodName(@Valid @RequestBody UserDto userDto) {
        userDataService.register(userDto);
        return ResponseGenerator.generateResponseOK("User registered successfully");
    }

}
