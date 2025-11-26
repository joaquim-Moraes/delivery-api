package com.deliverytech.delivery_api.DTO.Response;

/**
 * DTO de resposta para item de pedido.
 * Inclui dados do produto e quantidade.
 */
public class ItemPedidoResponseDTO {

    private Long produtoId;
    private String produtoNome;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;

    public ItemPedidoResponseDTO() {
    }

    public ItemPedidoResponseDTO(Long produtoId, String produtoNome, int quantidade, 
                                double precoUnitario, double subtotal) {
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
