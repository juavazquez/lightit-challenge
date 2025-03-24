package com.lightit.challenge.validator.impl;

import com.lightit.challenge.validator.ValidFileType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, MultipartFile> {

  private List<String> allowedTypes;

  @Override
  public void initialize(ValidFileType constraintAnnotation) {
    // Initialize allowed file extensions from the annotation
    allowedTypes = Arrays.asList(constraintAnnotation.allowedTypes());
  }

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if (file == null || file.isEmpty()) {
      return false; // Reject empty or null files
    }

    // Get the file's extension (or content type)
    String fileType = file.getContentType();

    if (fileType == null) {
      return false; // Reject files with no content type
    }

    // If the file type matches any of the allowed types, it's valid
    return allowedTypes.stream().anyMatch(fileType::equals);
  }
}
