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
    private String nome;
    private String categoria;
    private boolean disponibilidade;
    private double preco;


    @ManyToOne
    @JoinColumn(name = "restaurante_fk")
    private Restaurant restaurante;

    public Restaurant getRestaurant() {
        return restaurante;
    }

    public Produto setRestaurant(Restaurant restaurant) {
        this.restaurante = restaurant;
        return this;
    }

    public String getCategoria() {
        return categoria;
    }

    public Produto setCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Produto setNome(String nome) {
        this.nome = nome;
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

    public Produto() {
    }

    public Produto(String nome, String categoria, boolean disponibilidade, double preco, Restaurant restaurant) {
        this.nome = nome;
        this.categoria = categoria;
        this.disponibilidade = disponibilidade;
        this.preco = preco;
        this.restaurante = restaurant;
    }
}
