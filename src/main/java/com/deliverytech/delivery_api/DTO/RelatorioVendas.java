package com.deliverytech.delivery_api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RelatorioVendas", description = "Relatório de vendas agrupadas por restaurante")
public class RelatorioVendas {
    
    @Schema(description = "ID único do restaurante", example = "1")
    private Long restauranteId;
    
    @Schema(description = "Nome do restaurante", example = "O Rei do Hambúrguer")
    private String restauranteNome;
    
    @Schema(description = "Total de pedidos do restaurante no período", example = "15")
    private int totalPedidos;
    
    @Schema(description = "Valor total de vendas do restaurante no período", example = "375.50")
    private double totalVendas;
    
    @Schema(description = "Valor médio por pedido (totalVendas / totalPedidos)", example = "25.03")
    private double ticketMedio;

    public RelatorioVendas(Long restauranteId, String restauranteNome, int totalPedidos, double totalVendas) {
        this.restauranteId = restauranteId;
        this.restauranteNome = restauranteNome;
        this.totalPedidos = totalPedidos;
        this.totalVendas = totalVendas;
        this.ticketMedio = totalPedidos > 0 ? totalVendas / totalPedidos : 0;
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

    public double getTicketMedio() {
        return ticketMedio;
    }

    public void setTicketMedio(double ticketMedio) {
        this.ticketMedio = ticketMedio;
    }
}
