package com.deliverytech.delivery_api.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;
    private String categoria;
    private boolean disponibilidade;
    private double preco;


    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Produto setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public String getCategoria() {
        return categoria;
    }

    public Produto setCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }


    public Long getId() {
        return produtoId;
    }

    public double getPreco(){
        return preco;
    }
    public Produto setPreco(double preco){
        this.preco = preco;
        return this;
    }

    public Produto setId(Long id) {
        this.produtoId = id;
        return this;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public Produto setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
        return this;
    }
}
