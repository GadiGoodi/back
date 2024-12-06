package com.gagoo.thiscoding.global.s3.controller.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PathValidator implements ConstraintValidator<ValidateImagePath, String> {

    private ValidateImagePath validateImagePath;

    @Override
    public void initialize(ValidateImagePath constraintAnnotation) {
        this.validateImagePath = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Enum<?>[] enumValues = this.validateImagePath.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString()) ||
                        this.validateImagePath.ignoreCase() && value.equalsIgnoreCase(enumValue.toString())
                ) {
                    return true;
                }
            }
        }

        return false;
    }
}