package com.deliverytech.delivery_api.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para horário de funcionamento.
 * Formato: HH:MM-HH:MM (ex: 09:00-22:00)
 */
public class HorarioFuncionamentoValidator implements ConstraintValidator<ValidHorarioFuncionamento, String> {

    @Override
    public void initialize(ValidHorarioFuncionamento annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Deixar @NotNull/NotBlank validar isso
        }

        // Formato: HH:MM-HH:MM
        String pattern = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$";
        
        if (!value.matches(pattern)) {
            return false;
        }

        // Validar que hora inicial < hora final
        String[] partes = value.split("-");
        String[] horaInicio = partes[0].split(":");
        String[] horaFim = partes[1].split(":");

        int horaInicioInt = Integer.parseInt(horaInicio[0]) * 60 + Integer.parseInt(horaInicio[1]);
        int horaFimInt = Integer.parseInt(horaFim[0]) * 60 + Integer.parseInt(horaFim[1]);

        return horaInicioInt < horaFimInt;
    }
}
