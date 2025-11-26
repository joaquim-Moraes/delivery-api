package com.deliverytech.delivery_api.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validação customizada para categoria de restaurante/produto.
 * Valores permitidos: FAST_FOOD, PIZZARIA, JAPONESA, ITALIANA, BRASILEIRA, OUTROS
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoriaValidator.class)
@Documented
public @interface ValidCategoria {
    String message() default "Categoria inválida. Valores permitidos: FAST_FOOD, PIZZARIA, JAPONESA, ITALIANA, BRASILEIRA, OUTROS";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
