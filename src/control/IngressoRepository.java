package com.example.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.model.Ingresso;

/**
 * Repositório em memória para Ingresso.
 * Usa o 'tipo' como chave única.
 */
public class IngressoRepository {
    private Map<String, Ingresso> ingressos = new HashMap<>();

    public Ingresso save(Ingresso ingresso) {
        ingressos.put(ingresso.getTipo(), ingresso);
        return ingresso;
    }

    public Optional<Ingresso> findByTipo(String tipo) {
        return Optional.ofNullable(ingressos.get(tipo));
    }

    public List<Ingresso> findAll() {
        return new ArrayList<>(ingressos.values());
    }

    public void deleteByTipo(String tipo) {
        ingressos.remove(tipo);
    }
}
