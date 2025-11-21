package com.deliverytech.delivery_api.Repository;

import com.deliverytech.delivery_api.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByAtivoTrue();
    Optional<Cliente> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);

}
