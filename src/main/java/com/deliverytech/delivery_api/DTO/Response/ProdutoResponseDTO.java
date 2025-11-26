package com.deliverytech.delivery_api.DTO.Response;

/**
 * DTO de resposta para dados do produto.
 * Inclui disponibilidade e detalhes do restaurante.
 */
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String categoria;
    private double preco;
    private boolean disponibilidade;
    private Long restauranteId;
    private String restauranteNome;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(Long id, String nome, String categoria, double preco, 
                             boolean disponibilidade, Long restauranteId, String restauranteNome) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.disponibilidade = disponibilidade;
        this.restauranteId = restauranteId;
        this.restauranteNome = restauranteNome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getRestauranteNome() {
        return restauranteNome;
    }

    public void setRestauranteNome(String restauranteNome) {
        this.restauranteNome = restauranteNome;
    }
}
