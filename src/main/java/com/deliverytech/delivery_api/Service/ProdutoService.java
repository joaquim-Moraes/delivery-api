package com.deliverytech.delivery_api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Repository.ProdutoRepository;
import com.deliverytech.delivery_api.Repository.RestauranteRepository;

@Service
@SuppressWarnings("null")
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private static final double PRECO_MINIMO = 0.01;
    private static final double PRECO_MAXIMO = 999999.99;

    /**
     * Cadastra um novo produto validando restaurante, preço e dados obrigatórios.
     * 
     * @param restauranteId ID do restaurante proprietário
     * @param produto Entidade Produto com dados para cadastro
     * @return Produto cadastrado com id gerado
     * @throws IllegalArgumentException se dados inválidos ou restaurante não existe
     */
    @Transactional
    public Produto cadastrarProduto(Long restauranteId, Produto produto) {
        validarId(restauranteId);

        Restaurant restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Restaurante com ID " + restauranteId + " não encontrado."));

        if (!restaurante.isAtivo()) {
            throw new IllegalArgumentException(
                    "Não é possível cadastrar produtos em restaurante inativo.");
        }

        validarDadosProduto(produto);
        validarPreco(produto.getPreco());
        validarNomeDuplicadoNoRestaurante(produto.getNome(), restauranteId);

        produto.setRestaurant(restaurante);
        produto.setDisponibilidade(true); // Novos produtos sempre iniciam disponíveis
        return produtoRepository.save(produto);
    }

    /**
     * Busca produtos de um restaurante que estão disponíveis.
     * 
     * @param restauranteId ID do restaurante
     * @return Lista de produtos disponíveis (disponibilidade = true)
     * @throws IllegalArgumentException se restaurante não existe
     */
    public List<Produto> buscarProdutosPorRestaurante(Long restauranteId) {
        validarId(restauranteId);

        Restaurant restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Restaurante com ID " + restauranteId + " não encontrado."));

        List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);
        return produtos.stream()
                .filter(Produto::isDisponibilidade)
                .toList();
    }

    /**
     * Busca produto por ID com validação de disponibilidade (aviso, não rejeição).
     * 
     * @param id ID do produto
     * @return Produto encontrado
     * @throws IllegalArgumentException se produto não existe
     */
    public Produto buscarProdutoPorId(Long id) {
        validarId(id);

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Produto com ID " + id + " não encontrado."));

        if (!produto.isDisponibilidade()) {
            // Avisa mas retorna o produto (para fins administrativos)
            System.out.println("Aviso: Produto '" + produto.getNome() + "' está indisponível.");
        }

        return produto;
    }

    /**
     * Atualiza produto com validações completas de dados e preço.
     * 
     * @param produtoId ID do produto a atualizar
     * @param dadosAtualizados Dados atualizados do produto
     * @return Produto atualizado
     * @throws IllegalArgumentException se produto não existe ou dados inválidos
     */
    @Transactional
    public Produto atualizarProduto(Long produtoId, Produto dadosAtualizados) {
        validarId(produtoId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Produto com ID " + produtoId + " não encontrado."));

        // Valida e atualiza nome
        if (dadosAtualizados.getNome() != null && !dadosAtualizados.getNome().isBlank()) {
            validarNomeDuplicadoNoRestauranteExcluindo(
                    dadosAtualizados.getNome(),
                    produto.getRestaurant().getId(),
                    produtoId);
            produto.setNome(dadosAtualizados.getNome());
        }

        // Valida e atualiza categoria
        if (dadosAtualizados.getCategoria() != null && !dadosAtualizados.getCategoria().isBlank()) {
            produto.setCategoria(dadosAtualizados.getCategoria());
        }

        // Valida e atualiza preço
        if (dadosAtualizados.getPreco() > 0) {
            validarPreco(dadosAtualizados.getPreco());
            produto.setPreco(dadosAtualizados.getPreco());
        }

        // Atualiza disponibilidade
        produto.setDisponibilidade(dadosAtualizados.isDisponibilidade());

        // Valida dados completos antes de salvar
        validarDadosProduto(produto);
        return produtoRepository.save(produto);
    }

    /**
     * Alterna a disponibilidade do produto (toggle).
     * Se disponível, torna indisponível; se indisponível, torna disponível.
     * 
     * @param produtoId ID do produto
     * @param disponivel Novo status de disponibilidade
     * @return Produto com disponibilidade atualizada
     * @throws IllegalArgumentException se produto não existe
     */
    @Transactional
    public Produto alterarDisponibilidade(Long produtoId, boolean disponivel) {
        validarId(produtoId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Produto com ID " + produtoId + " não encontrado."));

        produto.setDisponibilidade(disponivel);
        return produtoRepository.save(produto);
    }

    /**
     * Busca produtos por categoria (todos, independente de disponibilidade).
     * 
     * @param categoria Categoria do produto
     * @return Lista de produtos na categoria
     * @throws IllegalArgumentException se categoria for inválida
     */
    public List<Produto> buscarProdutosPorCategoria(String categoria) {
        validarCategoria(categoria);
        return produtoRepository.findByCategoria(categoria);
    }

    /**
     * Lista todos os produtos disponíveis (na plataforma).
     * 
     * @return Lista de produtos com disponibilidade = true
     */
    public List<Produto> listarDisponiveis() {
        return produtoRepository.findByDisponibilidadeTrue();
    }

    /**
     * Busca produtos por nome contendo o termo (case-insensitive).
     * 
     * @param nome Termo de busca
     * @return Lista de produtos que contêm o termo no nome
     * @throws IllegalArgumentException se nome for inválido
     */
    public List<Produto> buscarPorNome(String nome) {
        validarNome(nome);
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Remove um produto (soft delete ou hard delete conforme configuração).
     * 
     * @param produtoId ID do produto
     * @throws IllegalArgumentException se produto não existe
     */
    @Transactional
    public void removerProduto(Long produtoId) {
        validarId(produtoId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Produto com ID " + produtoId + " não encontrado."));

        produtoRepository.delete(produto);
    }

    /**
     * Lista todos os produtos de um restaurante (incluindo indisponíveis).
     * 
     * @param restauranteId ID do restaurante
     * @return Lista completa de produtos do restaurante
     * @throws IllegalArgumentException se restaurante não existe
     */
    public List<Produto> listarTodosPorRestaurante(Long restauranteId) {
        validarId(restauranteId);

        Restaurant restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Restaurante com ID " + restauranteId + " não encontrado."));

        return produtoRepository.findByRestaurante(restaurante);
    }

    // ==================== VALIDAÇÕES ====================

    /**
     * Valida dados obrigatórios do produto.
     */
    private void validarDadosProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        if (produto.getCategoria() == null || produto.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria do produto é obrigatória.");
        }
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero.");
        }
        if (produto.getRestaurant() == null) {
            throw new IllegalArgumentException("Produto deve estar associado a um restaurante.");
        }
    }

    /**
     * Valida preço dentro do intervalo permitido.
     */
    private void validarPreco(double preco) {
        if (preco < PRECO_MINIMO) {
            throw new IllegalArgumentException(
                    "Preço deve ser no mínimo R$ " + PRECO_MINIMO);
        }
        if (preco > PRECO_MAXIMO) {
            throw new IllegalArgumentException(
                    "Preço não pode exceder R$ " + PRECO_MAXIMO);
        }
    }

    /**
     * Valida ID (novo cadastro - não deve duplicar nome no restaurante).
     */
    private void validarNomeDuplicadoNoRestaurante(String nome, Long restauranteId) {
        List<Produto> existentes = produtoRepository.findByNomeAndRestauranteId(nome, restauranteId);
        if (!existentes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Produto com nome '" + nome + "' já existe neste restaurante.");
        }
    }

    /**
     * Valida duplicação de nome excluindo o produto atual (atualização).
     */
    private void validarNomeDuplicadoNoRestauranteExcluindo(String nome, Long restauranteId, Long produtoId) {
        List<Produto> existentes = produtoRepository.findByNomeAndRestauranteId(nome, restauranteId);
        if (existentes.stream().anyMatch(p -> !p.getId().equals(produtoId))) {
            throw new IllegalArgumentException(
                    "Produto com nome '" + nome + "' já existe neste restaurante.");
        }
    }

    /**
     * Valida formato do ID.
     */
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID deve ser um número positivo.");
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
}
