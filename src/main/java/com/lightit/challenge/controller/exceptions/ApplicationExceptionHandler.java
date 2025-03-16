package com.lightit.challenge.controller.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lightit.challenge.controller.response.CustomError;
import com.lightit.challenge.controller.response.ResponseGenerator;

import jakarta.persistence.EntityExistsException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CustomError> handleBadRequest(Exception e) {
    logger.info("Bad Request: " + e.getMessage());
    return ResponseGenerator.generateResponseError(
        HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleBadRequest(MethodArgumentNotValidException e) {
    logger.info("Bad Request: " + e.getMessage());
    Map<String, String> errors = new HashMap<>();

    e.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return ResponseGenerator.generateResponseError(HttpStatus.BAD_REQUEST, "Bad Request", errors);
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<CustomError> handleConflict(Exception e) {
    logger.error("Conflict: " + e.getMessage());
    return ResponseGenerator.generateResponseError(HttpStatus.CONFLICT, "Conflict", e.getMessage());
  }

  @ExceptionHandler({ RuntimeException.class, Exception.class })
  public ResponseEntity<CustomError> handleInternalServerError(Exception e) {
    logger.error("Internal Server Error: " + e);
    return ResponseGenerator.generateResponseError(
        HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }

}
