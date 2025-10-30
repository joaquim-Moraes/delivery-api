package com.deliverytech.delivery_api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Repository.ProdutoRepository;
import com.deliverytech.delivery_api.Repository.RestauranteRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restaurantRepository;

    // Cadastro de produto
    public Produto cadastrarProduto(Long restauranteId, Produto produto) {
        Optional<Restaurant> restauranteOpt = restaurantRepository.findById(restauranteId);
        if (restauranteOpt.isEmpty()) {
            throw new RuntimeException("Restaurante não encontrado.");
        }

        produto.setRestaurant(restauranteOpt.get());

        validarPreco(produto);
        return produtoRepository.save(produto);
    }

    // Validação de preço
    private void validarPreco(Produto produto) {
    if (produto.getPreco() <= 0) {
        throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }
    }

    // Buscar produtos por restaurante
    public List<Produto> listarPorRestaurante(Long restauranteId) {
        Restaurant restaurante = restaurantRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado."));
        return produtoRepository.findByRestaurant(restaurante);
    }

    // Buscar produtos por categoria
    public List<Produto> listarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    // Listar produtos disponíveis
    public List<Produto> listarDisponiveis() {
        return produtoRepository.findByDisponibilidadeTrue();
    }

    // Atualizar produto
    public Produto atualizarProduto(Long produtoId, Produto dadosAtualizados) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        produto.setCategoria(dadosAtualizados.getCategoria());
        produto.setDisponibilidade(dadosAtualizados.isDisponibilidade());
        produto.setPreco(dadosAtualizados.getPreco());

        validarPreco(produto);
        return produtoRepository.save(produto);
    }

    public Produto buscaPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(()-> new RuntimeException("Produto não encontrado"));
    }

    // Alterar disponibilidade
    public Produto alterarDisponibilidade(Long produtoId, boolean disponivel) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        produto.setDisponibilidade(disponivel);
        return produtoRepository.save(produto);
    }
}
