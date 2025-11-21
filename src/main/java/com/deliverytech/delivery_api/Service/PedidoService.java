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
@SuppressWarnings("null")
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


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

   
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
    }

    public List<Pedido> listarComFiltros(StatusPedido status, LocalDate dataInicio, LocalDate dataFim) {
        if (status != null && dataInicio != null && dataFim != null) {
           
            List<Pedido> porStatus = pedidoRepository.findByStatus(status);
            return porStatus.stream()
                    .filter(p -> !p.getDatePedido().isBefore(dataInicio) && !p.getDatePedido().isAfter(dataFim))
                    .toList();
        } else if (status != null) {
            return pedidoRepository.findByStatus(status);
        } else if (dataInicio != null && dataFim != null) {
            return pedidoRepository.findByDatePedidoBetween(dataInicio, dataFim);
        }
        return pedidoRepository.findAll();
    }

 
    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        
        
        validarTransicaoStatus(pedido.getStatus(), novoStatus);
        
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }


    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        
        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Não é possível cancelar um pedido já entregue.");
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }


    public List<Pedido> listarPedidosCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    
    public List<Pedido> listarPedidosRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId);
    }

    public Double calcularTotal(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            return 0.0;
        }
        
        return pedido.getItens().stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();
    }


    private void validarTransicaoStatus(StatusPedido statusAtual, StatusPedido novoStatus) {
        // Exemplo: não permitir voltar de ENTREGUE para outros estados
        if (statusAtual == StatusPedido.ENTREGUE && novoStatus != StatusPedido.ENTREGUE) {
            throw new RuntimeException("Pedido entregue não pode mudar de status.");
        }
    }

    
    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        return listarPedidosCliente(clienteId);
    }

    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public List<Pedido> listarPedidosPorPeriodo(LocalDate inicio, LocalDate fim) {
        return pedidoRepository.findByDatePedidoBetween(inicio, fim);
    }

    public Pedido alterarStatus(Long pedidoId, StatusPedido novoStatus) {
        return atualizarStatus(pedidoId, novoStatus);
    }

    public List<Pedido> gerarRelatorio(LocalDate inicio, LocalDate fim, String status) {
        return pedidoRepository.gerarRelatorio(inicio, fim, status);
    }
}