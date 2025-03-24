package com.lightit.challenge.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.lightit.challenge.validator.impl.FileTypeValidator;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidatorTest {

  FileTypeValidator validator = new FileTypeValidator();

  @Test
  public void testValidFileType() {
    // Mock MultipartFile
    MultipartFile file = mock(MultipartFile.class);
    when(file.getContentType()).thenReturn("image/jpeg");

    // Initialize the validator with allowed types
    ValidFileType annotation = mock(ValidFileType.class);
    when(annotation.allowedTypes()).thenReturn(new String[] {"image/jpeg", "image/png"});
    validator.initialize(annotation);

    // Check if the file type is valid
    boolean isValid = validator.isValid(file, null);
    assertTrue(isValid, "File type should be valid for image/jpeg");
  }

  @Test
  public void testInvalidFileType() {
    // Mock MultipartFile
    MultipartFile file = mock(MultipartFile.class);
    when(file.getContentType()).thenReturn("application/pdf");

    // Initialize the validator with allowed types
    ValidFileType annotation = mock(ValidFileType.class);
    when(annotation.allowedTypes()).thenReturn(new String[] {"image/jpeg", "image/png"});
    validator.initialize(annotation);

    // Check if the file type is invalid
    boolean isValid = validator.isValid(file, null);
    assertFalse(isValid, "File type should be invalid for application/pdf");
  }

  @Test
  public void testNullFile() {
    // Initialize the validator with allowed types
    ValidFileType annotation = mock(ValidFileType.class);
    when(annotation.allowedTypes()).thenReturn(new String[] {"image/jpeg", "image/png"});
    validator.initialize(annotation);

    // Check if the null file is invalid
    boolean isValid = validator.isValid(null, null);
    assertFalse(isValid, "Null file should be invalid");
  }

  @Test
  public void testEmptyFile() {
    // Mock MultipartFile
    MultipartFile file = mock(MultipartFile.class);
    when(file.isEmpty()).thenReturn(true);

    // Initialize the validator with allowed types
    ValidFileType annotation = mock(ValidFileType.class);
    when(annotation.allowedTypes()).thenReturn(new String[] {"image/jpeg", "image/png"});
    validator.initialize(annotation);

    // Check if the empty file is invalid
    boolean isValid = validator.isValid(file, null);
    assertFalse(isValid, "Empty file should be invalid");
  }

  @Test
  public void testFileWithoutContentType() {
    // Mock MultipartFile
    MultipartFile file = mock(MultipartFile.class);
    when(file.getContentType()).thenReturn(null);

    // Initialize the validator with allowed types
    ValidFileType annotation = mock(ValidFileType.class);
    when(annotation.allowedTypes()).thenReturn(new String[] {"image/jpeg", "image/png"});
    validator.initialize(annotation);

    // Check if the file without content type is invalid
    boolean isValid = validator.isValid(file, null);
    assertFalse(isValid, "File without content type should be invalid");
  }
}
