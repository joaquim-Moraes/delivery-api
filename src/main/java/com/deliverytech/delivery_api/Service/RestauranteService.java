package com.deliverytech.delivery_api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Repository.RestauranteRepository;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    // Cadastro de restaurante
    public Restaurant cadastrar(Restaurant restaurante) {
        validarDados(restaurante);
        restaurante.setAtivo(true); // Ativo por padrão
        return restauranteRepository.save(restaurante);
    }

    // Validação de dados básicos
    private void validarDados(Restaurant restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do restaurante é obrigatório.");
        }
        if (restaurante.getCategoria() == null || restaurante.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria do restaurante é obrigatória.");
        }
    }

    // Buscar restaurante por ID
    public Restaurant buscarPorId(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado."));
    }

    // Atualizar dados do restaurante
    public Restaurant atualizar(Long id, Restaurant dadosAtualizados) {
        Restaurant restaurante = buscarPorId(id);

        restaurante.setNome(dadosAtualizados.getNome());
        restaurante.setCategoria(dadosAtualizados.getCategoria());
        restaurante.setAvaliacao(dadosAtualizados.getAvaliacao());

        validarDados(restaurante);
        return restauranteRepository.save(restaurante);
    }

    // Alterar status (ativo/inativo)
    public Restaurant alterarStatus(Long id, boolean ativo) {
        Restaurant restaurante = buscarPorId(id);
        restaurante.setAtivo(ativo);
        return restauranteRepository.save(restaurante);
    }

    // Buscar por nome
    public List<Restaurant> buscarPorNome(String nome) {
        return restauranteRepository.findByNome(nome);
    }

    // Buscar por categoria
    public List<Restaurant> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria);
    }

    // Listar restaurantes ativos ordenados por avaliação
    public List<Restaurant> listarAtivosOrdenadosPorAvaliacao() {
        return restauranteRepository.findByAtivoTrueOrderByAvaliacaoDesc();
    }
}