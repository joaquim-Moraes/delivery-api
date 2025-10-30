package com.deliverytech.delivery_api.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Entity.Restaurant;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    Optional<Produto> findById(Long produtoId);
    List<Produto> findByRestaurant(Restaurant restaurant);
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByDisponibilidadeTrue();
}
