package com.lightit.challenge.controller.exceptions;

import com.lightit.challenge.controller.response.CustomError;
import com.lightit.challenge.controller.response.ResponseGenerator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

  @ExceptionHandler({IllegalArgumentException.class, BindingResultException.class})
  public ResponseEntity<CustomError> handleBadRequest(Exception e) {
    logger.info("Bad Request: " + e.getMessage());
    return ResponseGenerator.generateResponseError(
        HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage());
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<CustomError> handleConflict(Exception e) {
    logger.error("Conflict: " + e.getMessage());
    return ResponseGenerator.generateResponseError(HttpStatus.CONFLICT, "Conflict", e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<CustomError> handleNotFound(Exception e) {
    logger.error("Not Found: " + e.getMessage());
    return ResponseGenerator.generateResponseError(
        HttpStatus.NOT_FOUND, "Not Found", e.getMessage());
  }

  @ExceptionHandler({RuntimeException.class, Exception.class})
  public ResponseEntity<CustomError> handleInternalServerError(Exception e) {
    logger.error("Internal Server Error: " + e);
    return ResponseGenerator.generateResponseError(
        HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }
}
