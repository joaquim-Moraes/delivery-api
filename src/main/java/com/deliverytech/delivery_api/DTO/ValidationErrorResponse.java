package com.deliverytech.delivery_api.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta de erro de validação")
public class ValidationErrorResponse {
    
    @Schema(description = "Status da operação", example = "validation_error")
    private String status;
    
    @Schema(description = "Mensagem geral de validação", example = "Erro de validação encontrado")
    private String message;
    
    @Schema(description = "Código de erro", example = "VALIDATION_ERROR")
    private String errorCode;
    
    @Schema(description = "Código HTTP da resposta", example = "400")
    private int code;
    
    @Schema(description = "Lista de erros de validação por campo")
    private List<FieldError> fieldErrors;
    
    @Schema(description = "Timestamp do erro")
    private LocalDateTime timestamp;

    // Construtores
    public ValidationErrorResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = "validation_error";
        this.fieldErrors = new ArrayList<>();
        this.errorCode = "VALIDATION_ERROR";
        this.code = 400;
    }

    public ValidationErrorResponse(String message) {
        this();
        this.message = message;
    }

    // Factory method
    public static ValidationErrorResponse create(String message) {
        return new ValidationErrorResponse(message);
    }

    // Adiciona erro de campo
    public ValidationErrorResponse addFieldError(String field, String message, String rejectedValue) {
        this.fieldErrors.add(new FieldError(field, message, rejectedValue));
        return this;
    }

    public ValidationErrorResponse addFieldError(String field, String message) {
        this.fieldErrors.add(new FieldError(field, message, null));
        return this;
    }

    // Getters e Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Classe interna para erros de campo
    @Schema(description = "Erro de validação de um campo específico")
    public static class FieldError {
        @Schema(description = "Nome do campo com erro", example = "nome")
        private String field;
        
        @Schema(description = "Mensagem de erro do campo", example = "Nome é obrigatório")
        private String message;
        
        @Schema(description = "Valor rejeitado", example = "")
        private String rejectedValue;

        public FieldError(String field, String message, String rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }

        // Getters e Setters
        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(String rejectedValue) {
            this.rejectedValue = rejectedValue;
        }
    }
}
