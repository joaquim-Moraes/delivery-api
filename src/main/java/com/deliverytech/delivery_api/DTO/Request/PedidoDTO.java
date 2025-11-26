package com.deliverytech.delivery_api.DTO.Request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para criação de pedidos.
 * Valida cliente e items em cascata.
 */
public class PedidoDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Pedido deve conter pelo menos um item")
    @Valid
    private List<ItemPedidoDTO> itens;

    public PedidoDTO() {
    }

    public PedidoDTO(Long clienteId, List<ItemPedidoDTO> itens) {
        this.clienteId = clienteId;
        this.itens = itens;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
