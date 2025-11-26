package com.deliverytech.delivery_api.Validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para categoria.
 * Categorias permitidas: FAST_FOOD, PIZZARIA, JAPONESA, ITALIANA, BRASILEIRA, OUTROS
 */
public class CategoriaValidator implements ConstraintValidator<ValidCategoria, String> {

    private static final Set<String> CATEGORIAS_VALIDAS = new HashSet<>(Arrays.asList(
        "FAST_FOOD", "PIZZARIA", "JAPONESA", "ITALIANA", "BRASILEIRA", "OUTROS"
    ));

    @Override
    public void initialize(ValidCategoria annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Deixar @NotNull/NotBlank validar isso
        }

        return CATEGORIAS_VALIDAS.contains(value.toUpperCase());
    }
}
