package com.deliverytech.delivery_api.Controller;

import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    public Restaurant cadastrar(@RequestBody Restaurant restaurante) {
        return restauranteService.cadastrar(restaurante);
    }

    @GetMapping("/{id}")
    public Restaurant buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Restaurant atualizar(@PathVariable Long id, @RequestBody Restaurant dadosAtualizados) {
        return restauranteService.atualizar(id, dadosAtualizados);
    }

    @PutMapping("/{id}/status")
    public Restaurant alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        return restauranteService.alterarStatus(id, ativo);
    }

    @GetMapping("/nome")
    public List<Restaurant> buscarPorNome(@RequestParam String nome) {
        return restauranteService.buscarPorNome(nome);
    }

    @GetMapping("/categoria")
    public List<Restaurant> buscarPorCategoria(@RequestParam String categoria) {
        return restauranteService.buscarPorCategoria(categoria);
    }

    @GetMapping("/ativos")
    public List<Restaurant> listarAtivosOrdenadosPorAvaliacao() {
        return restauranteService.listarAtivosOrdenadosPorAvaliacao();
    }
}