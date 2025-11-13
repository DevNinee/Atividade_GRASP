package com.example.model;
// ============================================
// ARQUIVO: ItemCompra.java
// LOCALIZAÇÃO: src/model/
// ============================================

/**
 * Representa um item na compra (vínculo entre ingresso e quantidade).
 * Padrão GRASP aplicado: INFORMATION EXPERT
 * Justificativa: ItemCompra é especialista em calcular o subtotal
 * de um item, pois possui as informações necessárias (ingresso e quantidade).
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
     * Justificativa: ItemCompra conhece o ingresso e a quantidade,
     * portanto é o especialista para calcular o subtotal.
     * Isso mantém ALTA COESÃO na classe.
     */
    public double calcularSubtotal() {
        return ingresso.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("%dx %s = R$ %.2f",
                quantidade, ingresso.getTipo(), calcularSubtotal());
    }
}
