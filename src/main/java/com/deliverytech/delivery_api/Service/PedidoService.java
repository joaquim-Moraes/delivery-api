package com.deliverytech.delivery_api.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.Entity.Cliente;
import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Pedido.StatusPedido;
import com.deliverytech.delivery_api.Repository.ClienteRepository;
import com.deliverytech.delivery_api.Repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Criar novo pedido
    public Pedido criarPedido(Long clienteId, Pedido pedido) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        pedido.setCliente(clienteOpt.get());
        pedido.setDatePedido(LocalDate.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        return pedidoRepository.save(pedido);
    }

    // Buscar pedidos por cliente
    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    // Buscar pedidos por status
    public List<Pedido> listarPedidosPorStatus(String status) {
        return pedidoRepository.findByStatus(status);
    }

    // Buscar pedidos por intervalo de datas
    public List<Pedido> listarPedidosPorPeriodo(LocalDate inicio, LocalDate fim) {
        return pedidoRepository.findByDatePedidoBetween(inicio, fim);
    }

    // Alterar status do pedido
    public Pedido alterarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    // Gerar relatório de pedidos por período e status
    public List<Pedido> gerarRelatorio(LocalDate inicio, LocalDate fim, String status) {
        return pedidoRepository.gerarRelatorio(inicio, fim, status);
    }

    // Buscar pedido por ID
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
    }
}