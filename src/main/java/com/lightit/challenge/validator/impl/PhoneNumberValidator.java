package com.lightit.challenge.validator.impl;

import com.lightit.challenge.validator.ValidPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    // Regular expression for validating phone numbers (supports formats like
    // "+1234567890" or "1234567890")
    private static final String PHONE_PATTERN = "^(\\+\\d{1,3})?\\d{10}$";
    private static final Pattern PATTERN = Pattern.compile(PHONE_PATTERN);

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }
        return PATTERN.matcher(phoneNumber).matches();
    }

}
