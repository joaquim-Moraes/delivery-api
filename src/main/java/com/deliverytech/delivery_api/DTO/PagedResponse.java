package com.deliverytech.delivery_api.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta paginada da API")
public class PagedResponse<T> {
    
    @Schema(description = "Status da operação", example = "success")
    private String status;
    
    @Schema(description = "Mensagem descritiva", example = "Listagem obtida com sucesso")
    private String message;
    
    @Schema(description = "Lista de dados paginados")
    private List<T> data;
    
    @Schema(description = "Informações de paginação")
    private PaginationInfo pagination;
    
    @Schema(description = "Código HTTP da resposta", example = "200")
    private int code;
    
    @Schema(description = "Timestamp da resposta")
    private LocalDateTime timestamp;

    // Construtores
    public PagedResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = "success";
    }

    public PagedResponse(List<T> data, int currentPage, int pageSize, long totalElements, 
                         String message, int code) {
        this();
        this.data = data;
        this.message = message;
        this.code = code;
        this.pagination = new PaginationInfo(currentPage, pageSize, totalElements);
    }

    // Factory method
    public static <T> PagedResponse<T> success(List<T> data, int currentPage, int pageSize, 
                                               long totalElements, String message) {
        return new PagedResponse<>(data, currentPage, pageSize, totalElements, message, 200);
    }

    public static <T> PagedResponse<T> success(List<T> data, int currentPage, int pageSize, 
                                               long totalElements) {
        return new PagedResponse<>(data, currentPage, pageSize, totalElements, 
                                  "Listagem obtida com sucesso", 200);
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PaginationInfo getPagination() {
        return pagination;
    }

    public void setPagination(PaginationInfo pagination) {
        this.pagination = pagination;
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

    // Classe interna para informações de paginação
    @Schema(description = "Informações de paginação")
    public static class PaginationInfo {
        @Schema(description = "Página atual (zero-based)", example = "0")
        private int currentPage;
        
        @Schema(description = "Tamanho da página", example = "10")
        private int pageSize;
        
        @Schema(description = "Total de elementos", example = "50")
        private long totalElements;
        
        @Schema(description = "Total de páginas", example = "5")
        private long totalPages;

        public PaginationInfo(int currentPage, int pageSize, long totalElements) {
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalElements = totalElements;
            this.totalPages = (totalElements + pageSize - 1) / pageSize; // Calcula total de páginas
        }

        // Getters e Setters
        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public long getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(long totalPages) {
            this.totalPages = totalPages;
        }
    }
}
