package com.deliverytech.delivery_api.Entity;

import java.io.Serializable;
import java.util.Objects; // Importe java.util.Objects

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
@Embeddable
class ItemPedidoPK implements Serializable {
    @ManyToOne
    private Pedido pedido;
    @ManyToOne
    private Produto produto;

    public ItemPedidoPK() {}
    
    // Getters e Setters (MANTIDOS)
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    // Implementação ESSENCIAL de equals() e hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoPK that = (ItemPedidoPK) o;
        // Compara por Pedido e Produto
        return Objects.equals(pedido, that.pedido) &&
               Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, produto);
    }
}