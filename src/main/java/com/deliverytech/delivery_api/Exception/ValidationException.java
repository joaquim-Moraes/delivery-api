package com.deliverytech.delivery_api.Exception;

/**
 * Exceção lançada quando dados de entrada são inválidos.
 * HTTP Status: 400 Bad Request
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String fieldName;
    private String fieldValue;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public ValidationException(String fieldName, String fieldValue, String message) {
        super(message);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
