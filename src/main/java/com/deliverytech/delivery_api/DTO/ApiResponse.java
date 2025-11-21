package com.deliverytech.delivery_api.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta padronizada da API")
public class ApiResponse<T> {
    
    @Schema(description = "Status da operação (sucesso/erro)", example = "success")
    private String status;
    
    @Schema(description = "Mensagem descritiva da operação", example = "Produto criado com sucesso")
    private String message;
    
    @Schema(description = "Dados da resposta")
    private T data;
    
    @Schema(description = "Código HTTP da resposta", example = "200")
    private int code;
    
    @Schema(description = "Timestamp da resposta", example = "2025-11-21T10:30:00")
    private LocalDateTime timestamp;
    
    @Schema(description = "Caminho da requisição", example = "/api/produtos")
    private String path;

    // Constructores
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = "success";
    }

    public ApiResponse(String status, String message, T data, int code) {
        this();
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ApiResponse(String status, String message, int code) {
        this();
        this.status = status;
        this.message = message;
        this.code = code;
    }

    // Success factory methods
    public static <T> ApiResponse<T> success(T data, String message, int code) {
        return new ApiResponse<>("success", message, data, code);
    }

    public static <T> ApiResponse<T> success(T data, int code) {
        return new ApiResponse<>("success", "Operação realizada com sucesso", data, code);
    }

    public static ApiResponse<?> success(String message, int code) {
        return new ApiResponse<>("success", message, code);
    }

    
    public static ApiResponse<?> error(String message, int code) {
        ApiResponse<?> response = new ApiResponse<>("error", message, code);
        return response;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
