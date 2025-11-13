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
 * Serviço para gerenciamento de ingressos (CRUD).
 * Centraliza as operações de criação, leitura, atualização e exclusão.
 */
public class IngressoService {
    private List<Ingresso> ingressos;

    public IngressoService() {
        this.ingressos = new ArrayList<>();
    }

    /**
     * CREATE: Cria um novo ingresso
     */
    public Ingresso criarIngresso(String tipo, double preco, int quantidade) {
        Ingresso ingresso = new Ingresso(tipo, preco, quantidade);
        ingressos.add(ingresso);
        System.out.println("[CREATE] Ingresso criado: " + ingresso);
        return ingresso;
    }

    /**
     * READ: Lista todos os ingressos
     */
    public List<Ingresso> listarIngressos() {
        return new ArrayList<>(ingressos);
    }

    /**
     * READ: Busca ingresso por tipo
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
     * UPDATE: Atualiza preço de um ingresso
     */
    public void atualizarPreco(String tipo, double novoPreco) {
        Optional<Ingresso> ingressoOpt = getIngresso(tipo);
        if (ingressoOpt.isPresent()) {
            Ingresso ingresso = ingressoOpt.get();
            ingresso.setPreco(novoPreco);
            System.out.println("[UPDATE] Preço atualizado: " + ingresso);
        } else {
            System.out.println("[UPDATE] Ingresso não encontrado: " + tipo);
        }
    }

    /**
     * UPDATE: Atualiza quantidade disponível
     */
    public void atualizarQuantidade(String tipo, int novaQuantidade) {
        Optional<Ingresso> ingressoOpt = getIngresso(tipo);
        if (ingressoOpt.isPresent()) {
            Ingresso ingresso = ingressoOpt.get();
            ingresso.setQuantidadeDisponivel(novaQuantidade);
            System.out.println("[UPDATE] Quantidade atualizada: " + ingresso);
        } else {
            System.out.println("[UPDATE] Ingresso não encontrado: " + tipo);
        }
    }

    /**
     * DELETE: Remove um ingresso
     */
    public boolean deletarIngresso(String tipo) {
        Optional<Ingresso> ingressoOpt = getIngresso(tipo);
        if (ingressoOpt.isPresent()) {
            ingressos.remove(ingressoOpt.get());
            System.out.println("[DELETE] Ingresso removido: " + tipo);
            return true;
        }
        System.out.println("[DELETE] Ingresso não encontrado: " + tipo);
        return false;
    }
}
