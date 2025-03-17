package com.lightit.challenge.validator.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lightit.challenge.validator.FileTypeValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidatorImpl implements ConstraintValidator<FileTypeValidator, MultipartFile> {

    private List<String> allowedTypes;

    @Override
    public void initialize(FileTypeValidator constraintAnnotation) {
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
