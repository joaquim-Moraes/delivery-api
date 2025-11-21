package com.deliverytech.delivery_api.Exception;


public class ConflictException extends RuntimeException {
    
    private String errorCode;
    private String details;

    public ConflictException(String message) {
        super(message);
        this.errorCode = "CONFLICT";
    }

    public ConflictException(String message, String details) {
        super(message);
        this.errorCode = "CONFLICT";
        this.details = details;
    }

    public ConflictException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }
}
