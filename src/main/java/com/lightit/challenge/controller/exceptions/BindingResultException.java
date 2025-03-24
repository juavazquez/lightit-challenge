package com.lightit.challenge.controller.exceptions;

import org.springframework.validation.BindingResult;

public class BindingResultException extends RuntimeException {

  public BindingResultException(BindingResult bindingResult) {
    super(
        String.join(
            "; ",
            bindingResult.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toList()));
  }
}
