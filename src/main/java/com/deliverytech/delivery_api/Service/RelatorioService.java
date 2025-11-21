package com.deliverytech.delivery_api.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.DTO.ClienteAtivo;
import com.deliverytech.delivery_api.DTO.PedidosPorPeriodo;
import com.deliverytech.delivery_api.DTO.ProdutoMaisVendido;
import com.deliverytech.delivery_api.DTO.RelatorioVendas;
import com.deliverytech.delivery_api.Entity.ItemPedido;
import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Pedido.StatusPedido;
import com.deliverytech.delivery_api.Repository.PedidoRepository;

@Service
public class RelatorioService {

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Relatório: Vendas por Restaurante
     */
    public List<RelatorioVendas> relatorioVendasPorRestaurante(LocalDate dataInicio, LocalDate dataFim) {
        List<Pedido> pedidos = pedidoRepository.findByDatePedidoBetween(dataInicio, dataFim);
        
        Map<String, RelatorioVendas> vendasPorRestaurante = new HashMap<>();
        
        for (Pedido pedido : pedidos) {
            for (ItemPedido item : pedido.getItens()) {
                String restauranteKey = item.getProduto().getRestaurant().getId() + "-" + 
                                       item.getProduto().getRestaurant().getNome();
                
                double itemTotal = item.getPrecoUnitario() * item.getQuantidade();
                
                RelatorioVendas relatorio = vendasPorRestaurante.get(restauranteKey);
                if (relatorio == null) {
                    relatorio = new RelatorioVendas(
                        item.getProduto().getRestaurant().getId(),
                        item.getProduto().getRestaurant().getNome(),
                        1,
                        itemTotal
                    );
                } else {
                    relatorio.setTotalVendas(relatorio.getTotalVendas() + itemTotal);
                    relatorio.setTotalPedidos(relatorio.getTotalPedidos() + 1);
                }
                vendasPorRestaurante.put(restauranteKey, relatorio);
            }
        }
        
        return new ArrayList<>(vendasPorRestaurante.values());
    }

 
    public List<ProdutoMaisVendido> relatorioProdutosMaisVendidos(LocalDate dataInicio, LocalDate dataFim) {
        List<Pedido> pedidos = pedidoRepository.findByDatePedidoBetween(dataInicio, dataFim);
        
        Map<String, ProdutoMaisVendido> produtosMaisVendidos = new HashMap<>();
        
        for (Pedido pedido : pedidos) {
            for (ItemPedido item : pedido.getItens()) {
                String produtoKey = item.getProduto().getId().toString();
                
                double itemTotal = item.getPrecoUnitario() * item.getQuantidade();
                
                ProdutoMaisVendido produto = produtosMaisVendidos.get(produtoKey);
                if (produto == null) {
                    produto = new ProdutoMaisVendido(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getCategoria(),
                        item.getQuantidade(),
                        itemTotal
                    );
                } else {
                    produto.setQuantidadeVendida(produto.getQuantidadeVendida() + item.getQuantidade());
                    produto.setTotalVendido(produto.getTotalVendido() + itemTotal);
                }
                produtosMaisVendidos.put(produtoKey, produto);
            }
        }
        
        return produtosMaisVendidos.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getQuantidadeVendida(), p1.getQuantidadeVendida()))
                .limit(10)
                .toList();
    }

  
    public List<ClienteAtivo> relatorioClientesMaisAtivos(LocalDate dataInicio, LocalDate dataFim) {
        List<Pedido> pedidos = pedidoRepository.findByDatePedidoBetween(dataInicio, dataFim);
        
        Map<Long, ClienteAtivo> clientesAtivos = new HashMap<>();
        
        for (Pedido pedido : pedidos) {
            Long clienteId = pedido.getCliente().getId();
            
            double totalPedido = pedido.getItens().stream()
                    .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                    .sum();
            
            ClienteAtivo cliente = clientesAtivos.get(clienteId);
            if (cliente == null) {
                cliente = new ClienteAtivo(
                    pedido.getCliente().getId(),
                    pedido.getCliente().getNome(),
                    pedido.getCliente().getEmail(),
                    1,
                    totalPedido
                );
            } else {
                cliente.setTotalPedidos(cliente.getTotalPedidos() + 1);
                cliente.setGastoTotal(cliente.getGastoTotal() + totalPedido);
            }
            clientesAtivos.put(clienteId, cliente);
        }
        
        return clientesAtivos.values().stream()
                .sorted((c1, c2) -> Double.compare(c2.getGastoTotal(), c1.getGastoTotal()))
                .limit(10)
                .toList();
    }


    public List<PedidosPorPeriodo> relatorioPedidosPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<Pedido> pedidos = pedidoRepository.findByDatePedidoBetween(dataInicio, dataFim);
        
        Map<LocalDate, PedidosPorPeriodo> pedidosPorData = new HashMap<>();
        
        for (Pedido pedido : pedidos) {
            LocalDate data = pedido.getDatePedido();
            
            double totalPedido = pedido.getItens().stream()
                    .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                    .sum();
            
            PedidosPorPeriodo relatorio = pedidosPorData.get(data);
            if (relatorio == null) {
                int entregue = pedido.getStatus() == StatusPedido.ENTREGUE ? 1 : 0;
                int pendente = pedido.getStatus() == StatusPedido.PENDENTE ? 1 : 0;
                int cancelado = pedido.getStatus() == StatusPedido.CANCELADO ? 1 : 0;
                
                relatorio = new PedidosPorPeriodo(data, 1, totalPedido, entregue, pendente, cancelado);
            } else {
                relatorio.setTotalPedidos(relatorio.getTotalPedidos() + 1);
                relatorio.setTotalVendas(relatorio.getTotalVendas() + totalPedido);
                
                if (pedido.getStatus() == StatusPedido.ENTREGUE) {
                    relatorio.setPedidosEntregues(relatorio.getPedidosEntregues() + 1);
                } else if (pedido.getStatus() == StatusPedido.PENDENTE) {
                    relatorio.setPedidosPendentes(relatorio.getPedidosPendentes() + 1);
                } else if (pedido.getStatus() == StatusPedido.CANCELADO) {
                    relatorio.setPedidosCancelados(relatorio.getPedidosCancelados() + 1);
                }
            }
            pedidosPorData.put(data, relatorio);
        }
        
        return pedidosPorData.values().stream()
                .sorted((p1, p2) -> p1.getData().compareTo(p2.getData()))
                .toList();
    }
}
