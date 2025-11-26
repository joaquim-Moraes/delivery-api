package com.deliverytech.delivery_api.DTO.Response;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO de resposta para pedido completo.
 * Inclui todos os detalhes do pedido e seus itens.
 */
public class PedidoResponseDTO {

    private Long id;
    private Long clienteId;
    private String clienteNome;
    private LocalDate dataPedido;
    private String status;
    private double total;
    private List<ItemPedidoResponseDTO> itens;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Long id, Long clienteId, String clienteNome, LocalDate dataPedido, 
                            String status, double total, List<ItemPedidoResponseDTO> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ItemPedidoResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResponseDTO> itens) {
        this.itens = itens;
    }
}
