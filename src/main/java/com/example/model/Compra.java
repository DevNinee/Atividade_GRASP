package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma compra composta por itens (ItemCompra), associada a um Cliente.
 */
public class Compra {
    private String codigoCompra;
    private Cliente cliente;
    private List<ItemCompra> itens = new ArrayList<>();
    private String status = "Em Andamento"; // ou "Finalizada"

    public Compra(String codigoCompra, Cliente cliente) {
        this.codigoCompra = codigoCompra;
        this.cliente = cliente;
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
     * Adiciona um item à compra. Verifica disponibilidade no ingresso e atualiza estoque.
     */
    public void adicionarItem(Ingresso ingresso, int quantidade) {
        if (status.equals("Finalizada")) {
            throw new IllegalStateException("Não é possível adicionar item: compra já finalizada.");
        }
        if (!ingresso.verificarDisponibilidade(quantidade)) {
            throw new IllegalStateException("Ingressos insuficientes: " + ingresso.getTipo());
        }
        // atualiza estoque do ingresso
        ingresso.vender(quantidade);
        ItemCompra item = new ItemCompra(ingresso, quantidade);
        itens.add(item);
    }

    /**
     * Finaliza a compra, marcando status. Lança exceção se não houver itens.
     */
    public void finalizarCompra() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar uma compra vazia.");
        }
        this.status = "Finalizada";
    }

    /** Calcula o total somando os subtotais dos itens. */
    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemCompra::getSubtotal).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Compra %s - Cliente: %s - Status: %s\n", codigoCompra, cliente.getNome(), status));
        for (ItemCompra item : itens) {
            sb.append("  ").append(item).append("\n");
        }
        sb.append(String.format("Total: R$ %.2f", calcularTotal()));
        return sb.toString();
    }
}
