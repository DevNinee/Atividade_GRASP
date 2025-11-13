package com.example.service;
// ============================================
// ARQUIVO: IngressoService.java
// LOCALIZAÇÃO: src/service/
// ============================================

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.model.Ingresso;

/**
 * Serviço para gerenciamento de ingressos (CRUD operations).
 * Padrão GRASP aplicado: PURE FABRICATION
 * 
 * Justificativa: IngressoService é uma "fabricação pura" criada para:
 * - Centralizar operações CRUD de ingressos
 * - Manter ALTA COESÃO (responsabilidade única: gerenciar ingressos)
 * - Promover BAIXO ACOPLAMENTO entre Controller e dados
 * - Facilitar testes e manutenção
 */
public class IngressoService {
    private List<Ingresso> ingressos;

    public IngressoService() {
        this.ingressos = new ArrayList<>();
    }

    /**
     * CREATE - Cria um novo ingresso
     */
    public Ingresso criarIngresso(String tipo, double preco, int quantidade) {
        Ingresso ingresso = new Ingresso(tipo, preco, quantidade);
        ingressos.add(ingresso);
        System.out.println("[CREATE] " + ingresso);
        return ingresso;
    }

    /**
     * READ - Busca um ingresso por tipo
     */
    public Optional<Ingresso> getIngresso(String tipo) {
        for (Ingresso ingresso : ingressos) {
            if (ingresso.getTipo().equalsIgnoreCase(tipo)) {
                return Optional.of(ingresso);
            }
        }
        return Optional.empty();
    }

    /**
     * READ - Lista todos os ingressos
     */
    public List<Ingresso> listarIngressos() {
        return new ArrayList<>(ingressos);
    }

    /**
     * UPDATE - Atualiza preço de um ingresso
     */
    public boolean atualizarPreco(String tipo, double novoPreco) {
        Optional<Ingresso> ingresso = getIngresso(tipo);
        if (ingresso.isPresent()) {
            ingresso.get().setPreco(novoPreco);
            System.out.println("[UPDATE] Preço atualizado para " + tipo);
            return true;
        }
        return false;
    }

    /**
     * UPDATE - Atualiza quantidade disponível
     */
    public boolean atualizarQuantidade(String tipo, int novaQuantidade) {
        Optional<Ingresso> ingresso = getIngresso(tipo);
        if (ingresso.isPresent()) {
            ingresso.get().setQuantidadeDisponivel(novaQuantidade);
            System.out.println("[UPDATE] Quantidade atualizada para " + tipo);
            return true;
        }
        return false;
    }

    /**
     * DELETE - Remove um ingresso
     */
    public boolean removerIngresso(String tipo) {
        Optional<Ingresso> ingresso = getIngresso(tipo);
        if (ingresso.isPresent()) {
            ingressos.remove(ingresso.get());
            System.out.println("[DELETE] Ingresso removido: " + tipo);
            return true;
        }
        return false;
    }
}
