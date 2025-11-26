package com.deliverytech.delivery_api.DTO.Request;

import com.deliverytech.delivery_api.Validation.ValidCategoria;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para criação e atualização de produtos.
 * Valida: nome (2-50 caracteres), preço (R$ 0.01 a R$ 500), categoria, descrição (mínimo 10 caracteres).
 */
public class ProdutoDTO {

    @NotNull(message = "ID do restaurante é obrigatório")
    private Long restauranteId;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    @ValidCategoria
    private String categoria;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço mínimo é R$ 0.01")
    @DecimalMax(value = "500.00", message = "Preço máximo é R$ 500.00")
    private Double preco;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, message = "Descrição deve ter no mínimo 10 caracteres")
    private String descricao;

    private boolean disponibilidade = true;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long restauranteId, String nome, String categoria, Double preco, 
                     String descricao) {
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}
