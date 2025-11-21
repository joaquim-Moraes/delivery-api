package com.deliverytech.delivery_api.Exception;


public class BusinessException extends RuntimeException {
    
    private String errorCode;
    private String details;

    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
    }

    public BusinessException(String message, String details) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
        this.details = details;
    }

    public BusinessException(String message, String errorCode, String details) {
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
