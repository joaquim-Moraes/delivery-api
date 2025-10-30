package com.deliverytech.delivery_api.Entity;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate datePedido;

    @ManyToOne
    @JoinColumn(name ="cliente_id")
    private Cliente cliente;


    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private String relatorio;


    public enum StatusPedido {
        PENDENTE,
        EM_ANDAMENTO,
        ENTREGUE,
        CANCELADO
    }


    public String getRelatorio() {
        return relatorio;
    }

    public Pedido setRelatorio(String relatorio) {
        this.relatorio = relatorio;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public Pedido setStatus(StatusPedido status) {
        this.status = status;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Pedido setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDatePedido() {
        return datePedido;
    }

    public Pedido setDatePedido(LocalDate datePedido) {
        this.datePedido = datePedido;
        return this;
    }
}
