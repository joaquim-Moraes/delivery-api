package com.deliverytech.delivery_api.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para CEP brasileiro.
 * Aceita formatos: 12345-678 ou 12345678
 */
public class CEPValidator implements ConstraintValidator<ValidCEP, String> {

    @Override
    public void initialize(ValidCEP annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Deixar @NotNull/NotBlank validar isso
        }

        // Aceita: 12345-678 ou 12345678
        String cepPattern = "^\\d{5}-?\\d{3}$";
        return value.matches(cepPattern);
    }
}
