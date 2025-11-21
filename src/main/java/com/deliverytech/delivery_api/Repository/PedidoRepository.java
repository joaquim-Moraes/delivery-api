package com.deliverytech.delivery_api.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.Entity.Cliente;
import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Pedido.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByCliente(Cliente cliente);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findTop10ByOrderByDatePedidoDesc();
    List<Pedido> findByDatePedidoBetween(LocalDate inicio, LocalDate fim);
    
    @Query("SELECT DISTINCT p FROM Pedido p JOIN p.itens i JOIN i.produto pr JOIN pr.restaurante r WHERE r.id = :restauranteId")
    List<Pedido> findByRestauranteId(@Param("restauranteId") Long restauranteId);

    @Query("SELECT p FROM Pedido p WHERE p.datePedido BETWEEN :inicio AND :fim AND p.status = :status")
    List<Pedido> gerarRelatorio(@Param("inicio") LocalDate inicio,@Param("fim") LocalDate fim, @Param("status") String status);

    @Query("SELECT p FROM Pedido p WHERE p.datePedido BETWEEN :inicio AND :fim")
    List<Pedido> findPedidosPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

}
