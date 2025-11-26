package com.deliverytech.delivery_api.DTO.Response;

import java.time.LocalDate;

/**
 * DTO de resumo para pedido em listagens.
 * Contém apenas informações essenciais para exibição em listas.
 */
public class PedidoResumoDTO {

    private Long id;
    private String clienteNome;
    private LocalDate dataPedido;
    private String status;
    private double total;

    public PedidoResumoDTO() {
    }

    public PedidoResumoDTO(Long id, String clienteNome, LocalDate dataPedido, String status, double total) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
