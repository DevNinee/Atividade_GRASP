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
 * Serviço para gerenciamento de clientes (CRUD operations).
 * Padrão GRASP aplicado: PURE FABRICATION
 * 
 * Justificativa: ClienteService é uma "fabricação pura" criada para:
 * - Centralizar operações CRUD de clientes
 * - Manter ALTA COESÃO (responsabilidade única: gerenciar clientes)
 * - Promover BAIXO ACOPLAMENTO entre Controller e dados
 * - Facilitar testes e manutenção
 */
public class ClienteService {
    private List<Cliente> clientes;

    public ClienteService() {
        this.clientes = new ArrayList<>();
    }

    /**
     * CREATE - Cria um novo cliente
     */
    public Cliente criarCliente(String nome, String email) {
        Cliente cliente = new Cliente(nome, email);
        clientes.add(cliente);
        System.out.println("[CREATE] " + cliente);
        return cliente;
    }

    /**
     * READ - Busca um cliente por email
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
     * READ - Lista todos os clientes
     */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * UPDATE - Atualiza nome de um cliente
     */
    public boolean atualizarNome(String email, String novoNome) {
        Optional<Cliente> cliente = getCliente(email);
        if (cliente.isPresent()) {
            cliente.get().setNome(novoNome);
            System.out.println("[UPDATE] Nome atualizado para " + email);
            return true;
        }
        return false;
    }

    /**
     * DELETE - Remove um cliente
     */
    public boolean removerCliente(String email) {
        Optional<Cliente> cliente = getCliente(email);
        if (cliente.isPresent()) {
            clientes.remove(cliente.get());
            System.out.println("[DELETE] Cliente removido: " + email);
            return true;
        }
        return false;
    }
}
