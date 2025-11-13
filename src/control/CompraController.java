package com.example.controller;
// ============================================
// ARQUIVO: CompraController.java
// LOCALIZAÇÃO: src/
// ============================================

import java.util.List;

import com.example.model.Cliente;
import com.example.model.Compra;
import com.example.model.Ingresso;
import com.example.repository.CompraRepository; // IMPORT ADICIONADO

/**
 * Classe controladora que gerencia as operações de compra de ingressos.
 * Padrão GRASP aplicado: CONTROLLER
 * 
 * Justificativa: Esta classe atua como o controlador do sistema,
 * recebendo as requisições da interface (ou main) e coordenando
 * as operações entre os objetos do domínio (Compra, Ingresso, Cliente).
 * 
 * Por que CompraController atende ao padrão CONTROLLER:
 * 1. Representa o sistema como um todo (façade controller)
 * 2. Coordena as operações sem fazer todo o trabalho sozinha
 * 3. Delega responsabilidades para os especialistas apropriados
 * 4. Mantém BAIXO ACOPLAMENTO entre a interface e o domínio
 * 5. Promove ALTA COESÃO ao focar apenas em coordenar operações
 */
public class CompraController {
    // private List<Compra> comprasRealizadas; // REMOVIDO
    private CompraRepository compraRepository; // ADICIONADO
    private int contadorCompras;

    public CompraController() {
        // this.comprasRealizadas = new ArrayList<>(); // REMOVIDO
        this.compraRepository = new CompraRepository(); // ADICIONADO
        this.contadorCompras = 1;
    }

    /**
     * Padrão GRASP: CONTROLLER
     * Método aplicado em: iniciarNovaCompra()
     * Justificativa: O controller recebe a solicitação para iniciar
     * uma compra e coordena a criação do objeto Compra. Ele não faz
     * todo o trabalho, apenas coordena e delega.
     * Isso mantém BAIXO ACOPLAMENTO com outras camadas do sistema.
     */
    public Compra iniciarNovaCompra(Cliente cliente) {
        String codigoCompra = "C" + String.format("%04d", contadorCompras++);
        Compra compra = new Compra(codigoCompra, cliente);
        System.out.println("\n=== NOVA COMPRA INICIADA ===");
        System.out.println("Número: " + codigoCompra);
        System.out.println("Cliente: " + cliente.getNome());
        return compra;
    }

    /**
     * Padrão GRASP: CONTROLLER
     * Método aplicado em: adicionarItemNaCompra()
     * Justificativa: O controller recebe a operação e coordena
     * a adição do item, delegando para a compra (que é a especialista).
     * Isso centraliza o fluxo de controle e mantém a lógica organizada.
     */
    public void adicionarItemNaCompra(Compra compra, Ingresso ingresso, int quantidade) {
        try {
            compra.adicionarItem(ingresso, quantidade);  // Delega para Compra
            System.out.println("-> Item adicionado com sucesso!");
        } catch (IllegalStateException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     * Padrão GRASP: CONTROLLER e LOW COUPLING
     * Método aplicado em: finalizarCompra()
     * Justificativa: O controller coordena a finalização da compra
     * e a registra na lista de compras realizadas. Ele delega a
     * lógica de finalização para o objeto Compra (especialista).
     * Isso mantém BAIXO ACOPLAMENTO: a interface não precisa conhecer
     * detalhes de como uma compra é finalizada ou armazenada.
     */
    public void finalizarCompra(Compra compra) {
        try {
            compra.finalizarCompra();  // Delega para Compra
            // comprasRealizadas.add(compra); // REMOVIDO
            compraRepository.save(compra); // ADICIONADO
            System.out.println("-> Compra registrada no sistema!");
        } catch (IllegalStateException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     * Lista todas as compras realizadas
     * @return 
     */
    public List<Compra> listarComprasRealizadas() {
        System.out.println("\n=== COMPRAS REALIZADAS ===");
        List<Compra> compras = compraRepository.findAll(); // MODIFICADO

        if (compras.isEmpty()) {
            System.out.println("Nenhuma compra realizada ainda.");
        } else {
            for (Compra compra : compras) {
                System.out.println(compra);
            }
        }
        return compras;
    }

    /**
     * Calcula o total de todas as compras realizadas
     */
    public double calcularTotalCompras() {
        double total = 0.0;
        // MODIFICADO para usar o repositório
        for (Compra compra : compraRepository.findAll()) {
            if (compra.getStatus().equals("Finalizada")) {
                total += compra.calcularTotal();
            }
        }
        return total;
    }
}
