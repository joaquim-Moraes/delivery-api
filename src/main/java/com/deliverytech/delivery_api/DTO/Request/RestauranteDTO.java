package com.deliverytech.delivery_api.DTO.Request;

import com.deliverytech.delivery_api.Validation.ValidCategoria;
import com.deliverytech.delivery_api.Validation.ValidHorarioFuncionamento;
import com.deliverytech.delivery_api.Validation.ValidTelefone;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para criação e atualização de restaurantes.
 * Valida: nome, categoria, telefone, avaliação, taxa de entrega, tempo e horário.
 */
public class RestauranteDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    @ValidCategoria
    private String categoria;

    @NotNull(message = "Telefone é obrigatório")
    @ValidTelefone
    private String telefone;

    @NotNull(message = "Avaliação é obrigatória")
    @DecimalMin(value = "0.0", message = "Avaliação não pode ser negativa")
    @DecimalMax(value = "5.0", message = "Avaliação máxima é 5.0")
    private Double avaliacao;

    @NotNull(message = "Taxa de entrega é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false, message = "Taxa de entrega deve ser positiva")
    private Double taxaEntrega;

    @NotNull(message = "Tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo de entrega é 10 minutos")
    @Max(value = 120, message = "Tempo máximo de entrega é 120 minutos")
    private Integer tempoEntrega;

    @ValidHorarioFuncionamento
    private String horarioFuncionamento;

    public RestauranteDTO() {
    }

    public RestauranteDTO(String nome, String categoria, String telefone, Double avaliacao, 
                         Double taxaEntrega, Integer tempoEntrega, String horarioFuncionamento) {
        this.nome = nome;
        this.categoria = categoria;
        this.telefone = telefone;
        this.avaliacao = avaliacao;
        this.taxaEntrega = taxaEntrega;
        this.tempoEntrega = tempoEntrega;
        this.horarioFuncionamento = horarioFuncionamento;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(Double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public Integer getTempoEntrega() {
        return tempoEntrega;
    }

    public void setTempoEntrega(Integer tempoEntrega) {
        this.tempoEntrega = tempoEntrega;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }
}
