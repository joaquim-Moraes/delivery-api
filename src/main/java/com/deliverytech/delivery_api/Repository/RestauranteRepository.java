package com.deliverytech.delivery_api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.Entity.Restaurant;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurant,Long> {
    List<Restaurant> findByNome(String nome);
    List<Restaurant> findByCategoria(String categoria);
    List<Restaurant> findByAtivoTrue();
    List<Restaurant> findTop5ByOrderByNomeAsc();
    List<Restaurant> findByAtivoTrueOrderByAvaliacaoDesc();
}
