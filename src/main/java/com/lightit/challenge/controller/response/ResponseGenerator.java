package com.lightit.challenge.controller.response;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {

    public static <T> ResponseEntity<T> generateResponseOK(T body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public static ResponseEntity<CustomError> generateResponseError(
            HttpStatus status, String message, Map<String, String> error) {
        CustomError customError = new CustomError(status.value(), message, error);
        return ResponseEntity.status(status).body(customError);
    }

    public static ResponseEntity<CustomError> generateResponseError(
            HttpStatus status, String message, String detail) {
        Map<String, String> error = Map.of("detail", detail);
        return generateResponseError(status, message, error);
    }

    public static ResponseEntity<CustomError> generateResponseError(
            HttpStatus status, String message) {
        CustomError customError = new CustomError(status.value(), message, null);
        return ResponseEntity.status(status).body(customError);
    }

}
