package com.deliverytech.delivery_api.DTO;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PedidosPorPeriodo", description = "Análise de pedidos agrupados por dia no período")
public class PedidosPorPeriodo {
    
    @Schema(description = "Data do registro", example = "2025-11-21")
    private LocalDate data;
    
    @Schema(description = "Total de pedidos no dia", example = "5")
    private int totalPedidos;
    
    @Schema(description = "Valor total de vendas no dia", example = "125.50")
    private double totalVendas;
    
    @Schema(description = "Quantidade de pedidos entregues no dia", example = "3")
    private int pedidosEntregues;
    
    @Schema(description = "Quantidade de pedidos pendentes no dia", example = "2")
    private int pedidosPendentes;
    
    @Schema(description = "Quantidade de pedidos cancelados no dia", example = "0")
    private int pedidosCancelados;

    public PedidosPorPeriodo(LocalDate data, int totalPedidos, double totalVendas, 
                             int pedidosEntregues, int pedidosPendentes, int pedidosCancelados) {
        this.data = data;
        this.totalPedidos = totalPedidos;
        this.totalVendas = totalVendas;
        this.pedidosEntregues = pedidosEntregues;
        this.pedidosPendentes = pedidosPendentes;
        this.pedidosCancelados = pedidosCancelados;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(double totalVendas) {
        this.totalVendas = totalVendas;
    }

    public int getPedidosEntregues() {
        return pedidosEntregues;
    }

    public void setPedidosEntregues(int pedidosEntregues) {
        this.pedidosEntregues = pedidosEntregues;
    }

    public int getPedidosPendentes() {
        return pedidosPendentes;
    }

    public void setPedidosPendentes(int pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }

    public int getPedidosCancelados() {
        return pedidosCancelados;
    }

    public void setPedidosCancelados(int pedidosCancelados) {
        this.pedidosCancelados = pedidosCancelados;
    }
}
