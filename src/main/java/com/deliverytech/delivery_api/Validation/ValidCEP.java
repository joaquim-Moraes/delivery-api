package com.deliverytech.delivery_api.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validação customizada para formato de CEP brasileiro.
 * Aceita formatos: 12345-678 ou 12345678
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CEPValidator.class)
@Documented
public @interface ValidCEP {
    String message() default "CEP deve estar no formato 12345-678 ou 12345678";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
