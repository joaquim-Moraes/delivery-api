package com.deliverytech.delivery_api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.Entity.Cliente;
import com.deliverytech.delivery_api.Repository.ClienteRepository;

@Service
@SuppressWarnings("null")
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Cadastro de cliente
    public Cliente cadastrarCliente(Cliente cliente) {
        validarEmail(cliente.getEmail());

        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }

    private void validarEmail(String email) {
        Optional<Cliente> existente = clienteRepository.findByEmail(email);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
    }

    
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    public Cliente buscarPorEmail(String email){
        return clienteRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Cliente não encontrado"));
    }

    
    public Cliente atualizarCliente(Long id, Cliente dadosAtualizados) {
        Cliente cliente = buscarPorId(id);

        cliente.setNome(dadosAtualizados.getNome());
        cliente.setEmail(dadosAtualizados.getEmail());

        return clienteRepository.save(cliente);
    }

    
    public Cliente inativarCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        return clienteRepository.save(cliente);
    }

    // Listar clientes ativos
    public List<Cliente> listarClientesAtivos() {
        return clienteRepository.findByAtivoTrue();
    }
}