package com.deliverytech.delivery_api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Entity.Restaurant;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByRestaurante(Restaurant restaurant);
    List<Produto> findByDisponibilidadeTrue();
    List<Produto> findByPrecoLessThanEqual(double preco);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByNomeAndRestauranteId(String nome, Long restauranteId);
    
}
