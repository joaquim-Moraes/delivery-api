package com.deliverytech.delivery_api.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validação customizada para horário de funcionamento.
 * Formato: HH:MM-HH:MM (ex: 09:00-22:00)
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HorarioFuncionamentoValidator.class)
@Documented
public @interface ValidHorarioFuncionamento {
    String message() default "Horário deve estar no formato HH:MM-HH:MM (ex: 09:00-22:00)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
