package com.example.service;
// ============================================
// ARQUIVO: ClienteService.java
// LOCALIZAÇÃO: src/service/
// ============================================

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.model.Cliente;

/**
 * Serviço para gerenciamento de clientes (CRUD).
 * Centraliza as operações de criação, leitura, atualização e exclusão.
 */
public class ClienteService {
    private List<Cliente> clientes;

    public ClienteService() {
        this.clientes = new ArrayList<>();
    }

    /**
     * CREATE: Cria um novo cliente
     */
    public Cliente criarCliente(String nome, String email) {
        Cliente cliente = new Cliente(nome, email);
        clientes.add(cliente);
        System.out.println("[CREATE] Cliente criado: " + cliente);
        return cliente;
    }

    /**
     * READ: Lista todos os clientes
     */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * READ: Busca cliente por email
     */
    public Optional<Cliente> getCliente(String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    /**
     * UPDATE: Atualiza nome do cliente
     */
    public void atualizarNome(String email, String novoNome) {
        Optional<Cliente> clienteOpt = getCliente(email);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setNome(novoNome);
            System.out.println("[UPDATE] Cliente atualizado: " + cliente);
        } else {
            System.out.println("[UPDATE] Cliente não encontrado: " + email);
        }
    }

    /**
     * DELETE: Remove um cliente
     */
    public boolean deletarCliente(String email) {
        Optional<Cliente> clienteOpt = getCliente(email);
        if (clienteOpt.isPresent()) {
            clientes.remove(clienteOpt.get());
            System.out.println("[DELETE] Cliente removido: " + email);
            return true;
        }
        System.out.println("[DELETE] Cliente não encontrado: " + email);
        return false;
    }
}
