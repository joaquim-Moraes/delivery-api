package com.deliverytech.delivery_api.Controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Pedido.StatusPedido;
import com.deliverytech.delivery_api.Service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criarPedido(@RequestParam Long clienteId, @RequestBody Pedido pedido) {
        return pedidoService.criarPedido(clienteId, pedido);
    }

    @GetMapping("/{id}")
    public Pedido buscarPedido(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

  
    @GetMapping
    public List<Pedido> listarComFiltros(
            @RequestParam(required = false) StatusPedido status,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {
        return pedidoService.listarComFiltros(status, dataInicio, dataFim);
    }


    @PatchMapping("/{id}/status")
    public Pedido atualizarStatus(@PathVariable Long id, @RequestParam StatusPedido novoStatus) {
        return pedidoService.atualizarStatus(id, novoStatus);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> listarPedidosCliente(@PathVariable Long clienteId) {
        return pedidoService.listarPedidosCliente(clienteId);
    }

  
    @GetMapping("/restaurante/{restauranteId}")
    public List<Pedido> listarPedidosRestaurante(@PathVariable Long restauranteId) {
        return pedidoService.listarPedidosRestaurante(restauranteId);
    }

    @PostMapping("/calcular")
    public Map<String, Object> calcularTotal(@RequestBody Pedido pedido) {
        Double total = pedidoService.calcularTotal(pedido);
        
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("total", total);
        resposta.put("itens", pedido.getItens().size());
        resposta.put("calculado_em", LocalDate.now());
        
        return resposta;
    }

    
    
    @PostMapping("/cliente/{clienteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criarPedidoLegado(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
        return pedidoService.criarPedido(clienteId, pedido);
    }

    @GetMapping("/status")
    public List<Pedido> listarPorStatus(@RequestParam StatusPedido status) {
        return pedidoService.listarPedidosPorStatus(status);
    }

    @GetMapping("/periodo")
    public List<Pedido> listarPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return pedidoService.listarPedidosPorPeriodo(inicio, fim);
    }

    @PutMapping("/{id}/status")
    public Pedido alterarStatusLegado(@PathVariable Long id, @RequestParam StatusPedido status) {
        return pedidoService.alterarStatus(id, status);
    }

    @GetMapping("/relatorio")
    public List<Pedido> gerarRelatorio(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            @RequestParam String status
    ) {
        return pedidoService.gerarRelatorio(inicio, fim, status);
    }
}