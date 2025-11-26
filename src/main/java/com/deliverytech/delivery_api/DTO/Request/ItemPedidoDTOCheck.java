// Arquivo temporário para análise - não usar em produção
// Este arquivo foi criado para documentar a estrutura do ItemPedidoDTO

package com.deliverytech.delivery_api.DTO.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para itens de pedido na requisição.
 * Espera: produtoId, quantidade, precoUnitario (opcional - será preenchido automáticamente)
 */
public class ItemPedidoDTOCheck {

    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    private Integer quantidade;

    @DecimalMin(value = "0.01", message = "Preço unitário deve ser maior que 0.01")
    private Double precoUnitario; // Opcional - preenchido automaticamente se não enviado

    // Getters e Setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
