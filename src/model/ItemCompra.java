package com.example.model;
// ============================================
// ARQUIVO: ItemCompra.java
// LOCALIZAÇÃO: src/model/
// ============================================

/**
 * Representa um item individual em uma compra (Ingresso + quantidade).
 * Padrão GRASP aplicado: INFORMATION EXPERT
 * Justificativa: ItemCompra é o especialista sobre seus próprios dados
 * (ingresso e quantidade) e sabe calcular seu próprio subtotal.
 */
public class ItemCompra {
    private Ingresso ingresso;
    private int quantidade;

    public ItemCompra(Ingresso ingresso, int quantidade) {
        this.ingresso = ingresso;
        this.quantidade = quantidade;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Padrão GRASP: INFORMATION EXPERT
     * Método aplicado em: calcularSubtotal()
     * Justificativa: ItemCompra possui as informações necessárias
     * (preço do ingresso e quantidade) para calcular seu próprio subtotal.
     * Isso mantém ALTA COESÃO.
     */
    public double calcularSubtotal() {
        return ingresso.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("  %dx %s = R$ %.2f", 
                           quantidade, ingresso.getTipo(), calcularSubtotal());
    }
}
