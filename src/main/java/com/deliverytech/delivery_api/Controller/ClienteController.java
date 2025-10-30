package com.deliverytech.delivery_api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.Entity.Cliente;
import com.deliverytech.delivery_api.Service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteService.cadastrarCliente(cliente);
    }

    @GetMapping
    public List<Cliente> listarTodosClientes() {
        return clienteService.listarClientesAtivos();
    }

    @GetMapping("/{id}")
    public Cliente buscarClientesPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("/email")
    public Cliente buscaPorEmail(@RequestParam String email) {
        return clienteService.buscarPorEmail(email);
    }

    @PutMapping("/{id}")
    public Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        return clienteService.atualizarCliente(id, clienteAtualizado);
    }

    @PutMapping("/{id}/inativar")
    public Cliente inativarCliente(@PathVariable Long id) {
        return clienteService.inativarCliente(id);
    }

    @GetMapping("/ativos")
    public List<Cliente> listarClientesAtivos() {
        return clienteService.listarClientesAtivos();
    }
}