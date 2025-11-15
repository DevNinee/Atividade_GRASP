package com.example.model;
// ============================================
// ARQUIVO: Ingresso.java
// LOCALIZAÇÃO: src/
// ============================================

/**
 * Representa um ingresso para um show.
 * Padrão GRASP aplicado: INFORMATION EXPERT
 * Justificativa: Ingresso é especialista em suas próprias informações
 * (tipo, preço, disponibilidade) e sabe como verificar disponibilidade.
 */
public class Ingresso {
    private String tipo; // Ex: Pista, VIP, Camarote
    private double preco;
    private int quantidadeDisponivel;

    public Ingresso(String tipo, double preco, int quantidadeDisponivel) {
        this.tipo = tipo;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getTipo() { 
        return tipo; 
    }

    public double getPreco() { 
        return preco; 
    }

    public int getQuantidadeDisponivel() { 
        return quantidadeDisponivel; 
    }

    // --- MÉTODOS ADICIONADOS PARA UPDATE (CRUD) ---
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
    // --- FIM DAS ADIÇÕES ---

    /**
     * Padrão GRASP: INFORMATION EXPERT
     * Método aplicado em: verificarDisponibilidade()
     * Justificativa: Ingresso conhece sua própria quantidade disponível,
     * portanto é o especialista para verificar se há ingressos suficientes.
     * Isso mantém ALTA COESÃO.
     */
    public boolean verificarDisponibilidade(int quantidade) {
        return quantidadeDisponivel >= quantidade;
    }

    /**
     * Padrão GRASP: INFORMATION EXPERT
     * Método aplicado em: vender()
     * Justificativa: Ingresso gerencia seu próprio estoque,
     * mantendo a responsabilidade centralizada.
     */
    public void vender(int quantidade) {
        if (verificarDisponibilidade(quantidade)) {
            quantidadeDisponivel -= quantidade;
        } else {
            throw new IllegalStateException("Ingressos insuficientes: " + tipo);
        }
    }

    @Override
    public String toString() {
        return String.format("Ingresso [%s] R$ %.2f - Disponível: %d", 
                           tipo, preco, quantidadeDisponivel);
    }
}
