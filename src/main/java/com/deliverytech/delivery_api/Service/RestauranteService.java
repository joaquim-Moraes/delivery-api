package com.deliverytech.delivery_api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Repository.RestauranteRepository;

@Service
@SuppressWarnings("null")
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    
    public Restaurant cadastrar(Restaurant restaurante) {
        validarDados(restaurante);
        restaurante.setAtivo(true); 
        return restauranteRepository.save(restaurante);
    }

   
    private void validarDados(Restaurant restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do restaurante é obrigatório.");
        }
        if (restaurante.getCategoria() == null || restaurante.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria do restaurante é obrigatória.");
        }
    }

    
    public Restaurant buscarPorId(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado."));
    }

    
    public Restaurant atualizar(Long id, Restaurant dadosAtualizados) {
        Restaurant restaurante = buscarPorId(id);

        restaurante.setNome(dadosAtualizados.getNome());
        restaurante.setCategoria(dadosAtualizados.getCategoria());
        restaurante.setAvaliacao(dadosAtualizados.getAvaliacao());

        validarDados(restaurante);
        return restauranteRepository.save(restaurante);
    }

    
    public Restaurant alterarStatus(Long id, boolean ativo) {
        Restaurant restaurante = buscarPorId(id);
        restaurante.setAtivo(ativo);
        return restauranteRepository.save(restaurante);
    }

    
    public List<Restaurant> buscarPorNome(String nome) {
        return restauranteRepository.findByNome(nome);
    }
    public List<Restaurant> listarTodos() {
        return restauranteRepository.findAll();
    }
    public List<Restaurant> listarComFiltros(String categoria, Boolean ativo) {
        if (categoria != null && ativo != null) {
            return restauranteRepository.findByCategoria(categoria).stream()
                    .filter(r -> r.isAtivo() == ativo)
                    .toList();
        } else if (categoria != null) {
            return restauranteRepository.findByCategoria(categoria);
        } else if (ativo != null) {
            return restauranteRepository.findByAtivoTrue();
        } else {
            return restauranteRepository.findAll();
        }
    }
    public List<Restaurant> listarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria);
    }
    
    public List<Restaurant> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria);
    }

    public List<Restaurant> listarAtivosOrdenadosPorAvaliacao() {
        return restauranteRepository.findByAtivoTrueOrderByAvaliacaoDesc();
    }
}