package com.deliverytech.delivery_api.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta de erro padronizada")
public class ErrorResponse {
    
    @Schema(description = "Status da operação", example = "error")
    private String status;
    
    @Schema(description = "Mensagem de erro", example = "Recurso não encontrado")
    private String message;
    
    @Schema(description = "Código de erro", example = "NOT_FOUND")
    private String errorCode;
    
    @Schema(description = "Código HTTP da resposta", example = "404")
    private int code;
    
    @Schema(description = "Detalhes adicionais do erro")
    private String details;
    
    @Schema(description = "Caminho da requisição", example = "/api/produtos/999")
    private String path;
    
    @Schema(description = "Timestamp do erro")
    private LocalDateTime timestamp;

    // Construtores
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = "error";
    }

    public ErrorResponse(String message, String errorCode, int code, String details) {
        this();
        this.message = message;
        this.errorCode = errorCode;
        this.code = code;
        this.details = details;
    }

    public ErrorResponse(String message, String errorCode, int code) {
        this();
        this.message = message;
        this.errorCode = errorCode;
        this.code = code;
    }

    // Factory methods
    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(message, "NOT_FOUND", 404);
    }

    public static ErrorResponse badRequest(String message, String details) {
        return new ErrorResponse(message, "BAD_REQUEST", 400, details);
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(message, "BAD_REQUEST", 400);
    }

    public static ErrorResponse conflict(String message, String details) {
        return new ErrorResponse(message, "CONFLICT", 409, details);
    }

    public static ErrorResponse internalError(String message) {
        return new ErrorResponse(message, "INTERNAL_ERROR", 500);
    }

    public static ErrorResponse unauthorized(String message) {
        return new ErrorResponse(message, "UNAUTHORIZED", 401);
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
