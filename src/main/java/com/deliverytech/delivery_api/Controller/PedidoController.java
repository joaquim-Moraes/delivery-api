package com.deliverytech.delivery_api.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.DTO.ApiResponse;
import com.deliverytech.delivery_api.DTO.PagedResponse;
import com.deliverytech.delivery_api.DTO.Request.PedidoDTO;
import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Pedido.StatusPedido;
import com.deliverytech.delivery_api.Service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciar pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido com transação complexa")
    public ResponseEntity<ApiResponse<Pedido>> criarPedido(
            @Valid
            @RequestBody PedidoDTO pedidoDTO) {
        Pedido novoPedido = pedidoService.criarPedidoDTO(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(novoPedido, "Pedido criado com sucesso", 201));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna os dados completos do pedido incluindo todos os itens")
    public ResponseEntity<ApiResponse<Pedido>> buscarPedido(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(pedido, 200));
    }

    @GetMapping("/clientes/{clienteId}/pedidos")
    @Operation(summary = "Histórico de pedidos do cliente", description = "Retorna todos os pedidos de um cliente")
    public ResponseEntity<PagedResponse<Pedido>> buscarPedidosCliente(
            @Parameter(description = "ID do cliente", example = "1", required = true)
            @PathVariable Long clienteId,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Pedido> pedidos = pedidoService.buscarPedidosPorCliente(clienteId);
        int start = page * size;
        int end = Math.min(start + size, pedidos.size());
        List<Pedido> paginado = pedidos.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, pedidos.size(),
                "Histórico de pedidos obtido com sucesso"));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Altera o status do pedido com validação de transições")
    public ResponseEntity<ApiResponse<Pedido>> atualizarStatus(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novo status (PENDENTE, EM_ANDAMENTO, ENTREGUE, CANCELADO)", example = "EM_ANDAMENTO", required = true)
            @RequestParam StatusPedido novoStatus) {
        Pedido pedido = pedidoService.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(ApiResponse.success(pedido, "Status do pedido atualizado com sucesso", 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido se o status permitir")
    public ResponseEntity<ApiResponse<Pedido>> cancelarPedido(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id) {
        Pedido pedido = pedidoService.cancelarPedido(id);
        return ResponseEntity.ok(ApiResponse.success(pedido, "Pedido cancelado com sucesso", 200));
    }

    @PostMapping("/calcular")
    @Operation(summary = "Calcular total do pedido", description = "Calcula o total do pedido sem salvá-lo")
    public ResponseEntity<ApiResponse<Double>> calcularTotal(@RequestBody Pedido pedido) {
        double total = pedidoService.calcularTotalPedido(pedido.getItens());
        return ResponseEntity.ok(ApiResponse.success(total, "Total do pedido calculado com sucesso", 200));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos com filtros", description = "Lista pedidos com filtros opcionais por status e período")
    public ResponseEntity<PagedResponse<Pedido>> listarComFiltros(
            @Parameter(description = "Status do pedido (opcional)")
            @RequestParam(required = false) StatusPedido status,
            @Parameter(description = "Data inicial (opcional)")
            @RequestParam(required = false) LocalDate dataInicio,
            @Parameter(description = "Data final (opcional)")
            @RequestParam(required = false) LocalDate dataFim,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Pedido> pedidos = pedidoService.listarComFiltros(status, dataInicio, dataFim);
        int start = page * size;
        int end = Math.min(start + size, pedidos.size());
        List<Pedido> paginado = pedidos.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, pedidos.size(),
                "Pedidos obtidos com sucesso"));
    }
}
