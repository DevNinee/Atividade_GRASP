package com.example.model;

/**
 * Representa um item dentro de uma compra: um ingresso e a quantidade comprada.
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

    public double getSubtotal() {
        return ingresso.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s x%d -> R$ %.2f", ingresso.getTipo(), quantidade, getSubtotal());
    }
}
