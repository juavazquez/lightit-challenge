package com.lightit.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lightit.challenge.controller.response.ResponseGenerator;
import com.lightit.challenge.dto.UserDto;
import com.lightit.challenge.service.IUserDataService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class UserController {

    private final IUserDataService userDataService;

    public UserController(IUserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping(value = "/users", consumes = "multipart/form-data")
    public ResponseEntity<?> postMethodName(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        userDataService.register(userDto);
        return ResponseGenerator.generateResponseOK("User registered successfully");
    }

    private ResponseEntity<?> handleValidationErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder("");
        bindingResult.getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });
        return ResponseGenerator.generateResponseError(HttpStatus.BAD_REQUEST, "Bad Request",
                errorMessage.toString());
    }
}
