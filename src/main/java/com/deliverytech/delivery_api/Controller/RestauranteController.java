package com.deliverytech.delivery_api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Service.RestauranteService;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

   
    @PostMapping
    public Restaurant cadastrar(@RequestBody Restaurant restaurante) {
        return restauranteService.cadastrar(restaurante);
    }

    
    @GetMapping
    public List<Restaurant> listar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean ativo) {
        
        
        if (categoria != null || ativo != null) {
            return restauranteService.listarComFiltros(categoria, ativo);
        }
        return restauranteService.listarTodos();
    }

    
    @GetMapping("/{id}")
    public Restaurant buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Restaurant atualizar(@PathVariable Long id, @RequestBody Restaurant dadosAtualizados) {
        return restauranteService.atualizar(id, dadosAtualizados);
    }


    @PatchMapping("/{id}/status")
    public Restaurant atualizarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        return restauranteService.alterarStatus(id, ativo);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Restaurant> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarPorCategoria(categoria);
    }
}