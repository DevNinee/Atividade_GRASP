package com.example.model;
// ============================================
// ARQUIVO: Compra.java
// LOCALIZAÇÃO: src/model/
// ============================================

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma compra completa de ingressos.
 * Padrão GRASP aplicado: CREATOR, INFORMATION EXPERT
 * 
 * Justificativa CREATOR: Compra é responsável por criar ItemCompra porque:
 * - Contém/agrega múltiplos ItemCompra
 * - Registra os itens da compra
 * - Tem os dados necessários para inicializar ItemCompra
 * 
 * Justificativa INFORMATION EXPERT: Compra conhece todos os seus itens,
 * portanto é especialista em calcular o total da compra.
 */
public class Compra {
    private String codigoCompra;
    private Cliente cliente;
    private List<ItemCompra> itens;
    private String status; // "Em andamento" ou "Finalizada"
    private LocalDateTime dataCompra;

    public Compra(String codigoCompra, Cliente cliente) {
        this.codigoCompra = codigoCompra;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = "Em andamento";
        this.dataCompra = LocalDateTime.now();
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getStatus() {
        return status;
    }

    public List<ItemCompra> getItens() {
        return new ArrayList<>(itens); // Retorna cópia para proteger lista interna
    }

    /**
     * Padrão GRASP: CREATOR
     * Método aplicado em: adicionarItem()
     * Justificativa: Compra é o criador natural de ItemCompra porque
     * contém, agrega e manipula intensamente esses objetos.
     * Isso mantém BAIXO ACOPLAMENTO e ALTA COESÃO.
     */
    public void adicionarItem(Ingresso ingresso, int quantidade) {
        if (status.equals("Finalizada")) {
            throw new IllegalStateException("Não é possível adicionar itens a uma compra finalizada");
        }

        // Valida disponibilidade antes de criar o item
        if (!ingresso.verificarDisponibilidade(quantidade)) {
            throw new IllegalStateException("Quantidade insuficiente de ingressos: " + ingresso.getTipo());
        }

        // Cria o ItemCompra (padrão CREATOR)
        ItemCompra item = new ItemCompra(ingresso, quantidade);
        itens.add(item);

        // Atualiza o estoque do ingresso
        ingresso.vender(quantidade);

        System.out.println(item);
    }

    /**
     * Padrão GRASP: INFORMATION EXPERT
     * Método aplicado em: calcularTotal()
     * Justificativa: Compra conhece todos os seus itens e seus subtotais,
     * portanto é o especialista para calcular o total da compra.
     */
    public double calcularTotal() {
        double total = 0.0;
        for (ItemCompra item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    /**
     * Finaliza a compra, impedindo novas adições
     */
    public void finalizarCompra() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar uma compra sem itens");
        }
        this.status = "Finalizada";
        System.out.println("\n=== COMPRA FINALIZADA ===");
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Compra %s - %s\n", codigoCompra, cliente.getNome()));
        sb.append(String.format("Data: %s\n", 
                  dataCompra.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        sb.append("Itens:\n");
        for (ItemCompra item : itens) {
            sb.append(item).append("\n");
        }
        sb.append(String.format("TOTAL: R$ %.2f\n", calcularTotal()));
        sb.append(String.format("Status: %s", status));
        return sb.toString();
    }
}
