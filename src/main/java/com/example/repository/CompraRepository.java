package com.example.repository;

import com.example.model.Compra;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório em memória para Compra.
 * Usa o 'codigoCompra' como chave única.
 */
public class CompraRepository {
    private Map<String, Compra> compras = new HashMap<>();

    public Compra save(Compra compra) {
        compras.put(compra.getCodigoCompra(), compra);
        return compra;
    }

    public Optional<Compra> findByCodigo(String codigo) {
        return Optional.ofNullable(compras.get(codigo));
    }

    public List<Compra> findAll() {
        return new ArrayList<>(compras.values());
    }
}
