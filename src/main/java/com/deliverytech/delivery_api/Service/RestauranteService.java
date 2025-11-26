package com.deliverytech.delivery_api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Repository.RestauranteRepository;

@Service
@SuppressWarnings("null")
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    private static final double TAXA_BASE = 5.0;
    private static final double TAXA_POR_KM = 0.5;

    /**
     * Cadastra um novo restaurante com validações completas.
     * 
     * @param restaurante Entidade Restaurant com dados para cadastro
     * @return Restaurante cadastrado com id gerado
     * @throws IllegalArgumentException se dados obrigatórios forem inválidos
     */
    @Transactional
    public Restaurant cadastrar(Restaurant restaurante) {
        validarDados(restaurante);
        validarNomeDuplicado(restaurante.getNome());
        restaurante.setAtivo(true);
        return restauranteRepository.save(restaurante);
    }

    /**
     * Busca restaurante por ID com tratamento de erro customizado.
     * 
     * @param id ID do restaurante
     * @return Restaurante encontrado
     * @throws IllegalArgumentException se restaurante não existir
     */
    public Restaurant buscarRestaurantePorId(Long id) {
        validarId(id);
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Restaurante com ID " + id + " não encontrado."));
    }

    /**
     * Busca restaurantes por categoria com validação.
     * 
     * @param categoria Categoria do restaurante
     * @return Lista de restaurantes na categoria
     * @throws IllegalArgumentException se categoria for inválida
     */
    public List<Restaurant> buscarRestaurantesPorCategoria(String categoria) {
        validarCategoria(categoria);
        return restauranteRepository.findByCategoria(categoria);
    }

    /**
     * Busca apenas restaurantes ativos (disponíveis).
     * 
     * @return Lista de restaurantes com status ativo=true
     */
    public List<Restaurant> buscarRestaurantesDisponiveis() {
        return restauranteRepository.findByAtivoTrue();
    }

    /**
     * Atualiza restaurante existente com validação de existência e dados.
     * 
     * @param id ID do restaurante a atualizar
     * @param dadosAtualizados Dados atualizados do restaurante
     * @return Restaurante atualizado
     * @throws IllegalArgumentException se restaurante não existe ou dados inválidos
     */
    @Transactional
    public Restaurant atualizarRestaurante(Long id, Restaurant dadosAtualizados) {
        Restaurant restaurante = buscarRestaurantePorId(id);

        if (dadosAtualizados.getNome() != null && !dadosAtualizados.getNome().isBlank()) {
            validarNomeDuplicadoExcluindo(dadosAtualizados.getNome(), id);
            restaurante.setNome(dadosAtualizados.getNome());
        }

        if (dadosAtualizados.getCategoria() != null && !dadosAtualizados.getCategoria().isBlank()) {
            restaurante.setCategoria(dadosAtualizados.getCategoria());
        }

        if (dadosAtualizados.getAvaliacao() > 0) {
            validarAvaliacao(dadosAtualizados.getAvaliacao());
            restaurante.setAvaliacao(dadosAtualizados.getAvaliacao());
        }

        validarDados(restaurante);
        return restauranteRepository.save(restaurante);
    }

    /**
     * Calcula taxa de entrega com base na distância estimada pelo CEP.
     * Lógica: taxa base + (km estimado * taxa por km)
     * 
     * @param restauranteId ID do restaurante
     * @param cep CEP de destino (formato: 12345-678)
     * @return Taxa de entrega calculada
     * @throws IllegalArgumentException se restaurante não existe ou CEP inválido
     */
    public double calcularTaxaEntrega(Long restauranteId, String cep) {
        validarId(restauranteId);
        buscarRestaurantePorId(restauranteId); // Verifica se existe
        validarCep(cep);

        // Estima distância em km baseado no CEP (simplificado: usa os 2 primeiros dígitos)
        int distanciaEstimada = extrairDistanciaDosCep(cep);

        double taxa = TAXA_BASE + (distanciaEstimada * TAXA_POR_KM);
        return Math.round(taxa * 100.0) / 100.0; // Arredonda para 2 casas decimais
    }

    /**
     * Altera o status (ativo/inativo) de um restaurante.
     * 
     * @param id ID do restaurante
     * @param ativo Novo status
     * @return Restaurante com status atualizado
     */
    @Transactional
    public Restaurant alterarStatus(Long id, boolean ativo) {
        Restaurant restaurante = buscarRestaurantePorId(id);
        restaurante.setAtivo(ativo);
        return restauranteRepository.save(restaurante);
    }

    /**
     * Lista todos os restaurantes.
     * 
     * @return Lista completa de restaurantes
     */
    public List<Restaurant> listarTodos() {
        return restauranteRepository.findAll();
    }

    /**
     * Lista restaurantes ativos ordenados por avaliação (decrescente).
     * 
     * @return Lista de restaurantes ativos ordenada por avaliação
     */
    public List<Restaurant> listarAtivosOrdenadosPorAvaliacao() {
        return restauranteRepository.findByAtivoTrueOrderByAvaliacaoDesc();
    }

    /**
     * Busca restaurante por nome (case-insensitive parcial).
     * 
     * @param nome Nome do restaurante
     * @return Lista de restaurantes que correspondem ao nome
     */
    public List<Restaurant> buscarPorNome(String nome) {
        validarNome(nome);
        return restauranteRepository.findByNome(nome);
    }

    // ==================== VALIDAÇÕES ====================

    /**
     * Valida dados obrigatórios do restaurante.
     */
    private void validarDados(Restaurant restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do restaurante é obrigatório.");
        }
        if (restaurante.getCategoria() == null || restaurante.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria do restaurante é obrigatória.");
        }
        if (restaurante.getAvaliacao() < 0 || restaurante.getAvaliacao() > 5) {
            throw new IllegalArgumentException("Avaliação deve estar entre 0 e 5.");
        }
    }

    /**
     * Valida se o nome do restaurante já existe (novo cadastro).
     */
    private void validarNomeDuplicado(String nome) {
        List<Restaurant> existentes = restauranteRepository.findByNome(nome);
        if (!existentes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Restaurante com nome '" + nome + "' já existe.");
        }
    }

    /**
     * Valida se o nome já existe, excluindo o restaurante atual (atualização).
     */
    private void validarNomeDuplicadoExcluindo(String nome, Long id) {
        List<Restaurant> existentes = restauranteRepository.findByNome(nome);
        if (existentes.stream().anyMatch(r -> !r.getId().equals(id))) {
            throw new IllegalArgumentException(
                    "Restaurante com nome '" + nome + "' já existe.");
        }
    }

    /**
     * Valida formato do ID.
     */
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do restaurante deve ser um número positivo.");
        }
    }

    /**
     * Valida categoria.
     */
    private void validarCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria não pode ser vazia.");
        }
    }

    /**
     * Valida nome.
     */
    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
    }

    /**
     * Valida avaliação.
     */
    private void validarAvaliacao(int avaliacao) {
        if (avaliacao < 0 || avaliacao > 5) {
            throw new IllegalArgumentException("Avaliação deve estar entre 0 e 5.");
        }
    }

    /**
     * Valida formato do CEP (12345-678 ou 12345678).
     */
    private void validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("CEP não pode ser vazio.");
        }
        String cepLimpo = cep.replaceAll("-", "");
        if (!cepLimpo.matches("\\d{8}")) {
            throw new IllegalArgumentException(
                    "CEP deve conter 8 dígitos (formato: 12345-678 ou 12345678).");
        }
    }

    private int extrairDistanciaDosCep(String cep) {
        String cepLimpo = cep.replaceAll("-", "");
        int prefixo = Integer.parseInt(cepLimpo.substring(0, 2));
        return (prefixo / 10 + 1) * 5; // Estimativa simples
    }
}