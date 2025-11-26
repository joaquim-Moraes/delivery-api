package com.deliverytech.delivery_api.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validação customizada para telefone brasileiro.
 * Aceita formatos: (11) 9XXXX-XXXX, (11) 9 XXXX-XXXX, 11 9XXXX-XXXX, 119XXXXXXXX
 * Total: 10 ou 11 dígitos
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelefoneValidator.class)
@Documented
public @interface ValidTelefone {
    String message() default "Telefone inválido. Formato: (11) 9XXXX-XXXX ou 11 9XXXX-XXXX (10-11 dígitos)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
