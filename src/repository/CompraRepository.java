package com.example.repository;
// ============================================
// ARQUIVO: CompraRepository.java
// LOCALIZAÇÃO: src/repository/
// ============================================

import java.util.ArrayList;
import java.util.List;

import com.example.model.Compra;

/**
 * Repositório para persistência de compras.
 * Simula uma camada de persistência (poderia ser banco de dados).
 */
public class CompraRepository {
    private List<Compra> compras;

    public CompraRepository() {
        this.compras = new ArrayList<>();
    }

    /**
     * Salva uma compra no repositório
     */
    public void save(Compra compra) {
        compras.add(compra);
    }

    /**
     * Retorna todas as compras
     */
    public List<Compra> findAll() {
        return new ArrayList<>(compras);
    }

    /**
     * Busca uma compra pelo código
     */
    public Compra findByCodigo(String codigo) {
        for (Compra compra : compras) {
            if (compra.getCodigoCompra().equals(codigo)) {
                return compra;
            }
        }
        return null;
    }
}
