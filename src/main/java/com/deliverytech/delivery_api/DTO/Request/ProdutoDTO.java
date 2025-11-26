package com.deliverytech.delivery_api.DTO.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para criação e atualização de produtos.
 * Contém apenas os campos necessários para operações de escrita.
 */
public class ProdutoDTO {

    @NotNull(message = "ID do restaurante é obrigatório")
    private Long restauranteId;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(min = 3, max = 50, message = "Categoria deve ter entre 3 e 50 caracteres")
    private String categoria;

    @DecimalMin(value = "0.01", message = "Preço deve ser maior que R$ 0.01")
    private double preco;

    private boolean disponibilidade = true;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long restauranteId, String nome, String categoria, double preco) {
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.disponibilidade = true;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
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
}
