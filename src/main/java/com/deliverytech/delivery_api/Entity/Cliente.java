package com.deliverytech.delivery_api.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String email;
    private boolean ativo;

    public Long getId() {
        return id;
    }

    public Cliente setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Cliente setAtivo(boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Cliente setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Cliente setNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    public Cliente(){

    }
    public Cliente(String nome, String email, boolean ativo){
        this.nome= nome;
        this.email= email;
        this.ativo= ativo;
    }
}
