package com.lightit.challenge.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lightit.challenge.validator.impl.PhoneNumberValidator;
import org.junit.jupiter.api.Test;

public class PhoneNumberValidatorTest {

  PhoneNumberValidator validator = new PhoneNumberValidator();

  @Test
  public void testValidPhoneNumberWithCountryCode() {
    String validPhoneNumber = "+541234567890";
    boolean isValid = validator.isValid(validPhoneNumber, null);
    assertTrue(isValid, "Phone number with country code should be valid");
  }

  @Test
  public void testValidPhoneNumberWithoutCountryCode() {
    String validPhoneNumber = "1234567890";
    boolean isValid = validator.isValid(validPhoneNumber, null);
    assertTrue(isValid, "Phone number without country code should be valid");
  }

  @Test
  public void testInvalidPhoneNumberTooShort() {
    String invalidPhoneNumber = "12345";
    boolean isValid = validator.isValid(invalidPhoneNumber, null);
    assertFalse(isValid, "Phone number too short should be invalid");
  }

  @Test
  public void testInvalidPhoneNumberTooLong() {
    String invalidPhoneNumber = "123456789012345";
    boolean isValid = validator.isValid(invalidPhoneNumber, null);
    assertFalse(isValid, "Phone number too long should be invalid");
  }

  @Test
  public void testPhoneNumberWithLetters() {
    String invalidPhoneNumber = "+12345abc7890";
    boolean isValid = validator.isValid(invalidPhoneNumber, null);
    assertFalse(isValid, "Phone number with letters should be invalid");
  }

  @Test
  public void testPhoneNumberWithSpaces() {
    String invalidPhoneNumber = "+12 34567890";
    boolean isValid = validator.isValid(invalidPhoneNumber, null);
    assertFalse(isValid, "Phone number with spaces should be invalid");
  }

  @Test
  public void testNullPhoneNumber() {
    String nullPhoneNumber = null;
    boolean isValid = validator.isValid(nullPhoneNumber, null);
    assertFalse(isValid, "Null phone number should be invalid");
  }

  @Test
  public void testEmptyPhoneNumber() {
    String emptyPhoneNumber = "";
    boolean isValid = validator.isValid(emptyPhoneNumber, null);
    assertFalse(isValid, "Empty phone number should be invalid");
  }

  @Test
  public void testBlankPhoneNumber() {
    String blankPhoneNumber = "    ";
    boolean isValid = validator.isValid(blankPhoneNumber, null);
    assertFalse(isValid, "Blank phone number should be invalid");
  }
}
