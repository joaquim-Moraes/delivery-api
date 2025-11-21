package com.deliverytech.delivery_api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Parâmetros de filtro e paginação para requisições de listagem")
public class FilterRequest {
    
    @Schema(description = "Número da página (zero-based)", example = "0")
    private int page = 0;
    
    @Schema(description = "Tamanho da página", example = "10")
    private int size = 10;
    
    @Schema(description = "Campo para ordenação", example = "id")
    private String sort;
    
    @Schema(description = "Direção da ordenação (ASC/DESC)", example = "ASC")
    private String direction = "ASC";
    
    @Schema(description = "Filtro por status", example = "PENDENTE")
    private String status;
    
    @Schema(description = "Filtro por categoria", example = "Pizza")
    private String categoria;
    
    @Schema(description = "Data inicial para filtro (formato: yyyy-MM-dd)", example = "2025-01-01")
    private String dataInicio;
    
    @Schema(description = "Data final para filtro (formato: yyyy-MM-dd)", example = "2025-12-31")
    private String dataFim;
    
    @Schema(description = "Termo de busca/search", example = "Pizza")
    private String search;
    
    @Schema(description = "ID do restaurante para filtro", example = "1")
    private Long restauranteId;
    
    @Schema(description = "ID do cliente para filtro", example = "1")
    private Long clienteId;

    // Construtores
    public FilterRequest() {}

    public FilterRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    // Validação
    public void validate() {
        if (page < 0) {
            this.page = 0;
        }
        if (size <= 0) {
            this.size = 10;
        }
        if (size > 100) {
            this.size = 100; // Máximo de 100 itens por página
        }
        if (direction == null || (!direction.equalsIgnoreCase("ASC") && !direction.equalsIgnoreCase("DESC"))) {
            this.direction = "ASC";
        }
    }

    // Getters e Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
