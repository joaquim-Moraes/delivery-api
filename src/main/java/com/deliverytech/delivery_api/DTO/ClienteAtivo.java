package com.deliverytech.delivery_api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClienteAtivo", description = "Informações de clientes mais ativos no período")
public class ClienteAtivo {
    
    @Schema(description = "ID único do cliente", example = "5")
    private Long clienteId;
    
    @Schema(description = "Nome completo do cliente", example = "João Silva")
    private String clienteNome;
    
    @Schema(description = "Email do cliente", example = "joao@example.com")
    private String email;
    
    @Schema(description = "Total de pedidos realizados no período", example = "12")
    private int totalPedidos;
    
    @Schema(description = "Valor total gasto pelo cliente no período", example = "450.75")
    private double gastoTotal;
    
    @Schema(description = "Gasto médio por pedido (gastoTotal / totalPedidos)", example = "37.56")
    private double gastoMedio;

    public ClienteAtivo(Long clienteId, String clienteNome, String email, 
                        int totalPedidos, double gastoTotal) {
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.email = email;
        this.totalPedidos = totalPedidos;
        this.gastoTotal = gastoTotal;
        this.gastoMedio = totalPedidos > 0 ? gastoTotal / totalPedidos : 0;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public double getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(double gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public double getGastoMedio() {
        return gastoMedio;
    }

    public void setGastoMedio(double gastoMedio) {
        this.gastoMedio = gastoMedio;
    }
}
