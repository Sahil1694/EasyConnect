package com.Smart_Contact_Manager.Smart_Contact_Manager.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[]payload() default {};


}
