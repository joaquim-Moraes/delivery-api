package com.deliverytech.delivery_api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProdutoMaisVendido", description = "Informações de produtos mais vendidos no período")
public class ProdutoMaisVendido {
    
    @Schema(description = "ID único do produto", example = "1")
    private Long produtoId;
    
    @Schema(description = "Nome do produto", example = "Hamburger Premium")
    private String produtoNome;
    
    @Schema(description = "Categoria do produto", example = "Lanche")
    private String categoria;
    
    @Schema(description = "Total de unidades vendidas no período", example = "45")
    private int quantidadeVendida;
    
    @Schema(description = "Valor total vendido do produto no período", example = "900.00")
    private double totalVendido;
    
    @Schema(description = "Preço médio unitário do produto", example = "20.00")
    private double precoMedio;

    public ProdutoMaisVendido(Long produtoId, String produtoNome, String categoria, 
                              int quantidadeVendida, double totalVendido) {
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.categoria = categoria;
        this.quantidadeVendida = quantidadeVendida;
        this.totalVendido = totalVendido;
        this.precoMedio = quantidadeVendida > 0 ? totalVendido / quantidadeVendida : 0;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(double precoMedio) {
        this.precoMedio = precoMedio;
    }
}
