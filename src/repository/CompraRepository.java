package com.example.repository;
// ============================================
// ARQUIVO: CompraRepository.java
// LOCALIZAÇÃO: src/repository/
// ============================================

import java.util.ArrayList;
import java.util.List;

import com.example.model.Compra;

/**
 * Classe responsável por gerenciar a persistência de compras.
 * Padrão GRASP aplicado: PURE FABRICATION
 * 
 * Justificativa: CompraRepository é uma "fabricação pura" - não representa
 * um conceito do domínio real, mas foi criada para:
 * - Separar a lógica de persistência da lógica de negócio
 * - Manter ALTA COESÃO (responsabilidade única: gerenciar dados)
 * - Promover BAIXO ACOPLAMENTO (Controller não precisa saber como dados são armazenados)
 * - Facilitar mudanças futuras (ex: trocar lista em memória por banco de dados)
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
        return new ArrayList<>(compras); // Retorna cópia para proteger lista interna
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
