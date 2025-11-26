package com.deliverytech.delivery_api.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class ItemPedido {
    
    // 1. CHAVE COMPOSTA
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();
    
    // 2. RELACIONAMENTOS MAPEADOS
    @MapsId("pedido") 
    @ManyToOne
    @JoinColumn(name = "pedido_id") 
    @JsonBackReference
    private Pedido pedido;
    
    @MapsId("produto") 
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    
    // 3. ATRIBUTOS PRÓPRIOS
    private Integer quantidade;
    private Double precoUnitario; 

    public ItemPedido() {}

    // Construtor para facilitar a criação do ItemPedido
    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade) {
        // Define os componentes da PK
        this.id.setPedido(pedido);
        this.id.setProduto(produto);
        
        // Define os objetos de relacionamento
        this.pedido = pedido; 
        this.produto = produto; 
        
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco(); 
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ItemPedido setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public ItemPedido setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
        return this;
    }

    public ItemPedidoPK getId() {
        return id;
    }

    public ItemPedido setId(ItemPedidoPK id) {
        this.id = id;
        return this;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public ItemPedido setPedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public Produto getProduto() {
        return produto;
    }

    public ItemPedido setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }
}