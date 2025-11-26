package com.deliverytech.delivery_api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurantes", description = "Endpoints para gerenciar restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    @Operation(summary = "Cadastrar restaurante", description = "Cria um novo restaurante no sistema")
    public ResponseEntity<ApiResponse<Restaurant>> cadastrar(@RequestBody Restaurant restaurante) {
        Restaurant novoRestaurante = restauranteService.cadastrar(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(novoRestaurante, "Restaurante cadastrado com sucesso", 201));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Retorna os dados completos de um restaurante")
    public ResponseEntity<ApiResponse<Restaurant>> buscarPorId(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @PathVariable Long id) {
        Restaurant restaurante = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(ApiResponse.success(restaurante, 200));
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes", description = "Retorna lista de restaurantes disponíveis (ativos)")
    public ResponseEntity<PagedResponse<Restaurant>> listar(
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Restaurant> restaurantes = restauranteService.buscarRestaurantesDisponiveis();
        int start = page * size;
        int end = Math.min(start + size, restaurantes.size());
        List<Restaurant> paginado = restaurantes.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, restaurantes.size(),
                "Restaurantes disponíveis obtidos com sucesso"));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar restaurantes por categoria", description = "Retorna restaurantes de uma categoria específica")
    public ResponseEntity<PagedResponse<Restaurant>> buscarPorCategoria(
            @Parameter(description = "Categoria do restaurante", example = "Pizzaria", required = true)
            @PathVariable String categoria,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Restaurant> restaurantes = restauranteService.buscarRestaurantesPorCategoria(categoria);
        int start = page * size;
        int end = Math.min(start + size, restaurantes.size());
        List<Restaurant> paginado = restaurantes.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, restaurantes.size(),
                "Restaurantes da categoria obtidos com sucesso"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante", description = "Atualiza dados de um restaurante existente")
    public ResponseEntity<ApiResponse<Restaurant>> atualizar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody Restaurant dadosAtualizados) {
        Restaurant restaurante = restauranteService.atualizarRestaurante(id, dadosAtualizados);
        return ResponseEntity.ok(ApiResponse.success(restaurante, "Restaurante atualizado com sucesso", 200));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Ativar/Desativar restaurante", description = "Alterna o status ativo/inativo do restaurante")
    public ResponseEntity<ApiResponse<Restaurant>> atualizarStatus(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novo status (true=ativo, false=inativo)", example = "false", required = true)
            @RequestParam boolean ativo) {
        Restaurant restaurante = restauranteService.alterarStatus(id, ativo);
        return ResponseEntity.ok(ApiResponse.success(restaurante, "Status do restaurante alterado com sucesso", 200));
    }

    @GetMapping("/{restauranteId}/taxa-entrega/{cep}")
    @Operation(summary = "Calcular taxa de entrega", description = "Calcula a taxa de entrega para um CEP específico")
    public ResponseEntity<ApiResponse<Double>> calcularTaxaEntrega(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @PathVariable Long restauranteId,
            @Parameter(description = "CEP de destino (formato: 12345-678)", example = "01310-100", required = true)
            @PathVariable String cep) {
        double taxa = restauranteService.calcularTaxaEntrega(restauranteId, cep);
        return ResponseEntity.ok(ApiResponse.success(taxa, "Taxa de entrega calculada com sucesso", 200));
    }
}