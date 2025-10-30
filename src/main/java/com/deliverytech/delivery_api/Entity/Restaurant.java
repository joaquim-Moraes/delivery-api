package com.deliverytech.delivery_api.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String categoria;
    private boolean ativo;
    private int avaliacao;

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

    public Long getId() {
        return id;
    }

    public Restaurant setId(Long id) {
        this.id = id;
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
}
