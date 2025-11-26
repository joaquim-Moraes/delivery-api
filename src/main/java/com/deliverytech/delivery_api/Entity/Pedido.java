package com.deliverytech.delivery_api.Entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate datePedido;

    @ManyToOne
    @JoinColumn(name ="cliente_id")
    private Cliente cliente;

// Na classe Pedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemPedido> itens = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.PENDENTE;

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
    public Pedido() {
        this.datePedido = LocalDate.now();
    }

    public void addItem(ItemPedido item) {
        this.itens.add(item);
    }
    public List<ItemPedido> getItens() {
        return itens;
    }

    public Pedido setItens(List<ItemPedido> itens) {
        this.itens = itens;
        if (this.itens != null) {
            for (ItemPedido item : this.itens) {
                if (item != null) {
                    item.setPedido(this);
                    if (item.getId() != null) {
                        item.getId().setPedido(this);
                    }
                }
            }
        }
        return this;
    }

    public Pedido(Cliente cliente) {
        this(); 
        this.cliente = cliente;
    }


        public Pedido( LocalDate datePedido, Cliente cliente, StatusPedido status, String relatorio) {
            this.datePedido = datePedido;
            this.cliente = cliente;
            this.status = status;
            this.relatorio = relatorio;
        }
}
