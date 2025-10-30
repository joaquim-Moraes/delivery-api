package com.deliverytech.delivery_api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @PostMapping("/restaurante/{restauranteId}")
    public Produto cadastrarProduto(@PathVariable Long restauranteId, @RequestBody Produto produto) {
        return produtoService.cadastrarProduto(restauranteId, produto);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public List<Produto> listarPorRestaurante(@PathVariable Long restauranteId) {
        return produtoService.listarPorRestaurante(restauranteId);
    }

    // Listar produtos por categoria
    @GetMapping("/categoria")
    public List<Produto> listarPorCategoria(@RequestParam String categoria) {
        return produtoService.listarPorCategoria(categoria);
    }

 
    @GetMapping("/disponiveis")
    public List<Produto> listarDisponiveis() {
        return produtoService.listarDisponiveis();
    }


    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return produtoService.atualizarProduto(id, produtoAtualizado);
    }


    @PutMapping("/{id}/disponibilidade")
    public Produto alterarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        return produtoService.alterarDisponibilidade(id, disponivel);
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoService.buscaPorId(id);
    }
}