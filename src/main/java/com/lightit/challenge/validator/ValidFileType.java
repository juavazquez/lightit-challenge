package com.lightit.challenge.validator;

import com.lightit.challenge.validator.impl.FileTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = FileTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFileType {
  String message() default "Invalid file type";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  // Define allowed file extensions
  String[] allowedTypes();
}
