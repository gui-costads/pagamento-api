package com.pagamento_app.pagamento.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> compareValues(e.name(), value));
    }

    private boolean compareValues(String enumValue, String inputValue) {
        return ignoreCase ?
                enumValue.equalsIgnoreCase(inputValue) :
                enumValue.equals(inputValue);
    }
}
