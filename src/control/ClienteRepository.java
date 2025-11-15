package com.example.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.model.Cliente;

/**
 * Repositório em memória para Cliente.
 * Usa o 'email' como chave única.
 */
public class ClienteRepository {
    private Map<String, Cliente> clientes = new HashMap<>();

    public Cliente save(Cliente cliente) {
        clientes.put(cliente.getEmail(), cliente);
        return cliente;
    }

    public Optional<Cliente> findByEmail(String email) {
        return Optional.ofNullable(clientes.get(email));
    }

    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }

    public void deleteByEmail(String email) {
        clientes.remove(email);
    }
}
