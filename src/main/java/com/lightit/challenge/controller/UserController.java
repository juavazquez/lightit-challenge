package com.lightit.challenge.controller;

import com.lightit.challenge.controller.exceptions.BindingResultException;
import com.lightit.challenge.controller.response.CustomError;
import com.lightit.challenge.controller.response.ResponseGenerator;
import com.lightit.challenge.dto.UserInputDto;
import com.lightit.challenge.dto.UserOutputDto;
import com.lightit.challenge.service.IUserDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Users", description = "User operations")
public class UserController {

  private final IUserDataService userDataService;

  public UserController(IUserDataService userDataService) {
    this.userDataService = userDataService;
  }

  @PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Register a new user", description = "Register a new user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CustomError.class))
            })
      })
  public ResponseEntity<String> register(
      @Valid @ModelAttribute UserInputDto userDto, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      throw new BindingResultException(bindingResult);
    }

    userDataService.register(userDto);
    return ResponseGenerator.generateResponseOK("User registered successfully");
  }

  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get user by email", description = "Get user by email")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserOutputDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CustomError.class))
            })
      })
  public ResponseEntity<UserOutputDto> get(@RequestParam(required = true) String email) {

    return ResponseGenerator.generateResponseOK(userDataService.getUser(email));
  }
}
