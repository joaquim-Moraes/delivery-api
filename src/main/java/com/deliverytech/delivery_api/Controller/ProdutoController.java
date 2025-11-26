package com.deliverytech.delivery_api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.DTO.ApiResponse;
import com.deliverytech.delivery_api.DTO.PagedResponse;
import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciar produtos dos restaurantes")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @PostMapping
    @Operation(summary = "Cadastrar novo produto", description = "Cria um novo produto vinculado a um restaurante")
    public ResponseEntity<ApiResponse<Produto>> cadastrarProduto(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @RequestParam Long restauranteId, 
            @RequestBody Produto produto) {
        Produto novoProduto = produtoService.cadastrarProduto(restauranteId, produto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(novoProduto, "Produto criado com sucesso", 201));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna os dados completos de um produto específico")
    public ResponseEntity<ApiResponse<Produto>> buscarPorId(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(produto, 200));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    public ResponseEntity<ApiResponse<Produto>> atualizarProduto(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id, 
            @RequestBody Produto produtoAtualizado) {
        Produto produto = produtoService.atualizarProduto(id, produtoAtualizado);
        return ResponseEntity.ok(ApiResponse.success(produto, "Produto atualizado com sucesso", 200));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Remove um produto do sistema")
    public ResponseEntity<Void> removerProduto(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/disponibilidade")
    @Operation(summary = "Alterar disponibilidade", description = "Muda o status de disponibilidade do produto")
    public ResponseEntity<ApiResponse<Produto>> toggleDisponibilidade(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Novo status de disponibilidade", example = "true", required = true)
            @RequestParam boolean disponivel) {
        Produto produto = produtoService.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.ok(ApiResponse.success(produto, "Disponibilidade alterada com sucesso", 200));
    }

    @GetMapping("/restaurante/{restauranteId}")
    @Operation(summary = "Listar produtos por restaurante", description = "Retorna apenas produtos disponíveis de um restaurante")
    public ResponseEntity<PagedResponse<Produto>> listarPorRestaurante(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @PathVariable Long restauranteId,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Produto> produtos = produtoService.buscarProdutosPorRestaurante(restauranteId);
        int start = page * size;
        int end = Math.min(start + size, produtos.size());
        List<Produto> paginado = produtos.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, produtos.size(), "Produtos do restaurante obtidos com sucesso"));
    }


    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar produtos por categoria", description = "Retorna todos os produtos de uma categoria específica")
    public ResponseEntity<PagedResponse<Produto>> listarPorCategoria(
            @Parameter(description = "Categoria do produto", example = "Lanche", required = true)
            @PathVariable String categoria,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Produto> produtos = produtoService.buscarProdutosPorCategoria(categoria);
        int start = page * size;
        int end = Math.min(start + size, produtos.size());
        List<Produto> paginado = produtos.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, produtos.size(), "Produtos da categoria obtidos com sucesso"));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar produtos por nome", description = "Busca produtos que contenham o nome informado (case-insensitive)")
    public ResponseEntity<PagedResponse<Produto>> buscarPorNome(
            @Parameter(description = "Texto para busca no nome do produto", example = "Hambúrguer", required = true)
            @RequestParam String nome,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Produto> produtos = produtoService.buscarPorNome(nome);
        int start = page * size;
        int end = Math.min(start + size, produtos.size());
        List<Produto> paginado = produtos.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, produtos.size(), "Resultados da busca obtidos com sucesso"));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar produtos disponíveis", description = "Retorna todos os produtos que estão disponíveis para venda")
    public ResponseEntity<PagedResponse<Produto>> listarDisponiveis(
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Produto> produtos = produtoService.listarDisponiveis();
        int start = page * size;
        int end = Math.min(start + size, produtos.size());
        List<Produto> paginado = produtos.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, produtos.size(), "Produtos disponíveis obtidos com sucesso"));
    }
}