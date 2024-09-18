package com.Smart_Contact_Manager.Smart_Contact_Manager.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    public static final long MAX_FILE_SIZE = 1024 * 1024 * 2;


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("File is empty").addConstraintViolation();

            return true;
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size is too large").addConstraintViolation();
            return false;
        }
       return true;
    }
}
