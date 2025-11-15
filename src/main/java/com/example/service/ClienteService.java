package com.example.service;

import com.example.model.Cliente;
import com.example.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private ClienteRepository clienteRepository = new ClienteRepository();

    // CREATE
    public Cliente criarCliente(String nome, String email) {
        if (clienteRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Cliente com este email já existe.");
        }
        Cliente novo = new Cliente(nome, email);
        return clienteRepository.save(novo);
    }

    // READ (Single)
    public Optional<Cliente> getCliente(String email) {
        return clienteRepository.findByEmail(email);
    }

    // READ (All)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // UPDATE (Ex: apenas nome)
    public Cliente atualizarNomeCliente(String email, String novoNome) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + email));
        
        cliente.setNome(novoNome); // Requer setter no modelo
        
        return clienteRepository.save(cliente);
    }

    // DELETE
    public void deletarCliente(String email) {
        if (clienteRepository.findByEmail(email).isEmpty()) {
            throw new RuntimeException("Cliente não encontrado: " + email);
        }
        clienteRepository.deleteByEmail(email);
    }
}
