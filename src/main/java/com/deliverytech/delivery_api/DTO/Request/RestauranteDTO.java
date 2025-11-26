package com.deliverytech.delivery_api.DTO.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para criação e atualização de restaurantes.
 * Contém apenas os campos necessários para operações de escrita.
 */
public class RestauranteDTO {

    @NotBlank(message = "Nome do restaurante é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(min = 3, max = 50, message = "Categoria deve ter entre 3 e 50 caracteres")
    private String categoria;

    @DecimalMin(value = "0.0", inclusive = false, message = "Avaliação deve ser maior que 0")
    private int avaliacao;

    public RestauranteDTO() {
    }

    public RestauranteDTO(String nome, String categoria, int avaliacao) {
        this.nome = nome;
        this.categoria = categoria;
        this.avaliacao = avaliacao;
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

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }
}
