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
import com.deliverytech.delivery_api.Entity.Cliente;
import com.deliverytech.delivery_api.Service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciar clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente no sistema")
    public ResponseEntity<ApiResponse<Cliente>> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.cadastrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(novoCliente, "Cliente cadastrado com sucesso", 201));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados completos de um cliente")
    public ResponseEntity<ApiResponse<Cliente>> buscarPorId(
            @Parameter(description = "ID do cliente", example = "1", required = true)
            @PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success(cliente, 200));
    }

    @GetMapping
    @Operation(summary = "Listar clientes ativos", description = "Retorna lista de clientes com status ativo")
    public ResponseEntity<PagedResponse<Cliente>> listarAtivos(
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        List<Cliente> clientes = clienteService.listarClientesAtivos();
        int start = page * size;
        int end = Math.min(start + size, clientes.size());
        List<Cliente> paginado = clientes.subList(start, end);
        return ResponseEntity.ok(PagedResponse.success(paginado, page, size, clientes.size(), 
                "Clientes ativos obtidos com sucesso"));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar cliente por email", description = "Retorna cliente com email específico")
    public ResponseEntity<ApiResponse<Cliente>> buscarPorEmail(
            @Parameter(description = "Email do cliente", example = "cliente@example.com", required = true)
            @PathVariable String email) {
        Cliente cliente = clienteService.buscarPorEmail(email);
        return ResponseEntity.ok(ApiResponse.success(cliente, 200));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza dados de um cliente existente")
    public ResponseEntity<ApiResponse<Cliente>> atualizarCliente(
            @Parameter(description = "ID do cliente", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody Cliente clienteAtualizado) {
        Cliente cliente = clienteService.atualizarCliente(id, clienteAtualizado);
        return ResponseEntity.ok(ApiResponse.success(cliente, "Cliente atualizado com sucesso", 200));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Ativar/Desativar cliente", description = "Alterna o status ativo/inativo do cliente")
    public ResponseEntity<ApiResponse<Cliente>> alterarStatus(
            @Parameter(description = "ID do cliente", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novo status (true=ativo, false=inativo)", example = "false", required = true)
            @RequestParam boolean ativo) {
        Cliente cliente = clienteService.alterarStatus(id, ativo);
        return ResponseEntity.ok(ApiResponse.success(cliente, 
                "Status do cliente alterado com sucesso", 200));
    }
}