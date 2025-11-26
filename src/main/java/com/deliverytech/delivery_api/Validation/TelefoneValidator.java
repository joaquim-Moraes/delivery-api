package com.deliverytech.delivery_api.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para telefone brasileiro.
 * Aceita: (11) 9XXXX-XXXX, (11) 9 XXXX-XXXX, 11 9XXXX-XXXX, 119XXXXXXXX
 */
public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    @Override
    public void initialize(ValidTelefone annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Deixar @NotNull/NotBlank validar isso
        }

        // Remove espaços e hífens
        String cleaned = value.replaceAll("[\\s\\-()]", "");

        // Deve ter 10 ou 11 dígitos
        if (!cleaned.matches("^\\d{10,11}$")) {
            return false;
        }

        // Validação adicional: DDD válido (11-99)
        String ddd = cleaned.substring(0, 2);
        int dddNum = Integer.parseInt(ddd);
        
        if (dddNum < 11 || dddNum > 99) {
            return false;
        }

        return true;
    }
}
