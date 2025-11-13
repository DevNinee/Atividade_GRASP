package com.example.model;
// ============================================
// ARQUIVO: Cliente.java
// LOCALIZAÇÃO: src/
// ============================================

/**
 * Representa um cliente que compra ingressos.
 */
public class Cliente {
    private String nome;
    private String email;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() { 
        return nome; 
    }

    public String getEmail() { 
        return email; 
    }

    // --- MÉTODO ADICIONADO PARA UPDATE (CRUD) ---
    public void setNome(String nome) {
        this.nome = nome;
    }
    // --- FIM DA ADIÇÃO ---

    @Override
    public String toString() {
        return String.format("Cliente: %s (%s)", nome, email);
    }
}
