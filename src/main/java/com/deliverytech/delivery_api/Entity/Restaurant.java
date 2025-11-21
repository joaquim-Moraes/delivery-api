package com.deliverytech.delivery_api.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long restaurantId;
    private String nome;
    private String categoria;
    private boolean ativo;
    private int avaliacao;


    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    public boolean isAtivo() {
        return ativo;
    }

    public Restaurant setAtivo(boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Restaurant setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Long getrestauranteId() {
        return restaurantId;
    }

    public Long getId() {
        return restaurantId;
    }

    public Restaurant setrestauranteId(Long id) {
        this.restaurantId = id;
        return this;
    }

    public String getCategoria() {
        return categoria;
    }

    public Restaurant setCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public Restaurant setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
        return this;
    }

    public Restaurant() {}

    public Restaurant(String nome, String categoria, boolean ativo, int avaliacao) {
        this.nome = nome;
        this.categoria = categoria;
        this.ativo = ativo;
        this.avaliacao = avaliacao;
    }
}
