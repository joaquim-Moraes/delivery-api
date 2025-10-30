package com.deliverytech.delivery_api.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.Entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByStatus(String status);
    List<Pedido> findByDatePedidoBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT p FROM Pedido p WHERE p.datePedido BETWEEN :inicio AND :fim AND p.status = :status")
    List<Pedido> gerarRelatorio(@Param("inicio") LocalDate inicio,@Param("fim") LocalDate fim, @Param("status") String status);

}
