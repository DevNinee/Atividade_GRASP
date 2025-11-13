package com.example.model;
// ============================================
// ARQUIVO: Compra.java
// LOCALIZAÇÃO: src/model/
// ============================================

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma compra completa com vários itens.
 * Padrões GRASP aplicados: INFORMATION EXPERT e CREATOR
 */
public class Compra {
    private String codigoCompra;
    private Cliente cliente;
    private List<ItemCompra> itens;
    private String status; // Aberta ou Finalizada

    public Compra(String codigoCompra, Cliente cliente) {
        this.codigoCompra = codigoCompra;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = "Aberta";
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemCompra> getItens() {
        return itens;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Padrão GRASP: CREATOR
     * Método aplicado em: adicionarItem()
     * Justificativa: Compra agrega ItemCompra, portanto é responsável
     * por criar e adicionar itens na lista. Isso mantém o controle do
     * ciclo de vida dos ItemCompra centralizados na Compra.
     */
    public void adicionarItem(Ingresso ingresso, int quantidade) {
        if (!status.equals("Aberta")) {
            throw new IllegalStateException("Compra já finalizada!");
        }
        if (!ingresso.verificarDisponibilidade(quantidade)) {
            throw new IllegalStateException("Ingressos insuficientes: " + ingresso.getTipo());
        }
        
        ingresso.vender(quantidade); // Atualiza estoque
        ItemCompra item = new ItemCompra(ingresso, quantidade);
        itens.add(item);
    }

    /**
     * Padrão GRASP: INFORMATION EXPERT
     * Método aplicado em: calcularTotal()
     * Justificativa: Compra possui a lista de itens, portanto é a
     * especialista para calcular o total somando os subtotais.
     * Isso mantém ALTA COESÃO.
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
            throw new IllegalStateException("Não é possível finalizar uma compra sem itens!");
        }
        this.status = "Finalizada";
        System.out.println("\n=== COMPRA FINALIZADA ===");
        System.out.println("Código: " + codigoCompra);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Itens:");
        for (ItemCompra item : itens) {
            System.out.println("  - " + item);
        }
        System.out.printf("Total: R$ %.2f\n", calcularTotal());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Compra ").append(codigoCompra).append(" - ")
          .append(cliente.getNome()).append(" - ")
          .append(status).append("\n");
        for (ItemCompra item : itens) {
            sb.append("  ").append(item).append("\n");
        }
        sb.append(String.format("  Total: R$ %.2f", calcularTotal()));
        return sb.toString();
    }
}
