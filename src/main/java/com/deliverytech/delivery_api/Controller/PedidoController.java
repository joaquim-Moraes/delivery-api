package com.deliverytech.delivery_api.Controller;

import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Pedido.StatusPedido;
import com.deliverytech.delivery_api.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Criar novo pedido
    @PostMapping("/cliente/{clienteId}")
    public Pedido criarPedido(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
        return pedidoService.criarPedido(clienteId, pedido);
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    // Listar pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> listarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.listarPedidosPorCliente(clienteId);
    }

    // Listar pedidos por status
    @GetMapping("/status")
    public List<Pedido> listarPorStatus(@RequestParam String status) {
        return pedidoService.listarPedidosPorStatus(status);
    }

    // Listar pedidos por período
    @GetMapping("/periodo")
    public List<Pedido> listarPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return pedidoService.listarPedidosPorPeriodo(inicio, fim);
    }

    // Alterar status do pedido
    @PutMapping("/{id}/status")
    public Pedido alterarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        return pedidoService.alterarStatus(id, status);
    }

    // Gerar relatório por período e status
    @GetMapping("/relatorio")
    public List<Pedido> gerarRelatorio(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            @RequestParam String status
    ) {
        return pedidoService.gerarRelatorio(inicio, fim, status);
    }
}