package com.deliverytech.delivery_api.Repository;

import com.deliverytech.delivery_api.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurant,Long> {
    List<Restaurant> findByNome(String nome);
    List<Restaurant> findByCategoria(String categoria);
    List<Restaurant> findByAtivoTrueOrderByAvaliacaoDesc();

}
