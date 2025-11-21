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
@SuppressWarnings("null")
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restaurantRepository;

   
    public Produto cadastrarProduto(Long restauranteId, Produto produto) {
        Optional<Restaurant> restauranteOpt = restaurantRepository.findById(restauranteId);
        if (restauranteOpt.isEmpty()) {
            throw new RuntimeException("Restaurante não encontrado.");
        }

        produto.setRestaurant(restauranteOpt.get());

        validarPreco(produto);
        return produtoRepository.save(produto);
    }

   
    private void validarPreco(Produto produto) {
    if (produto.getPreco() <= 0) {
        throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }
    }

    
    public List<Produto> listarPorRestaurante(Long restauranteId) {
        Restaurant restaurante = restaurantRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado."));
        return produtoRepository.findByRestaurante(restaurante);
    }

    
    public List<Produto> listarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }
    
    public List<Produto> listarDisponiveis() {
        return produtoRepository.findByDisponibilidadeTrue();
    }

    
    public Produto atualizarProduto(Long produtoId, Produto dadosAtualizados) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        if (dadosAtualizados.getNome() != null) {
            produto.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getCategoria() != null) {
            produto.setCategoria(dadosAtualizados.getCategoria());
        }
        produto.setDisponibilidade(dadosAtualizados.isDisponibilidade());
        if (dadosAtualizados.getPreco() > 0) {
            produto.setPreco(dadosAtualizados.getPreco());
        }

        validarPreco(produto);
        return produtoRepository.save(produto);
    }

    public Produto buscaPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(()-> new RuntimeException("Produto não encontrado"));
    }

    
    public Produto alterarDisponibilidade(Long produtoId, boolean disponivel) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        produto.setDisponibilidade(disponivel);
        return produtoRepository.save(produto);
    }

    
    public void removerProduto(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        produtoRepository.delete(produto);
    }

    
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
