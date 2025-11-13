package com.example;
// ============================================
// ARQUIVO: SistemaIngressosShows.java
// ============================================

import com.example.controller.CompraController;
import com.example.model.Cliente;
import com.example.model.Compra;
import com.example.model.Ingresso;
import com.example.service.ClienteService; // IMPORT ADICIONADO
import com.example.service.IngressoService; // IMPORT ADICIONADO

/**
 * Classe principal para testar o sistema de vendas de ingressos.
 * Demonstra a aplicação dos padrões GRASP.
 */
public class SistemaIngressosShows {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE VENDAS DE INGRESSOS        ║");
        System.out.println("║     Aplicando Padrões GRASP            ║");
        System.out.println("╚════════════════════════════════════════╝");

    // ============================================
    // 1. GERENCIAMENTO (CRUD) DE INGRESSOS
    // ============================================
    IngressoService ingressoService = new IngressoService();

    // CREATE
    System.out.println("\n--- 1. CRUD de Ingressos ---");
    ingressoService.criarIngresso("Pista", 120.0, 100);
    ingressoService.criarIngresso("VIP", 220.0, 40);
    ingressoService.criarIngresso("Camarote", 380.0, 20);

    // READ (All)
    System.out.println("\n[READ] Ingressos disponíveis:");
    ingressoService.listarIngressos().forEach(System.out::println);

    // ============================================
    // 2. GERENCIAMENTO (CRUD) DE CLIENTES
    // ============================================
    ClienteService clienteService = new ClienteService();
        
    System.out.println("\n--- 2. CRUD de Clientes ---");
    // CREATE
    clienteService.criarCliente("Ana Souza", "ana@email.com");
    clienteService.criarCliente("Carlos Lima", "carlos@email.com");

    // ============================================
    // 3. FLUXO DE COMPRA (Usando o Controller)
    // ============================================
    System.out.println("\n--- 3. Fluxo de Compra ---");
    CompraController controller = new CompraController();

    // Buscando os dados dos serviços
    Cliente cliente1 = clienteService.getCliente("ana@email.com").get();
    Cliente cliente2 = clienteService.getCliente("carlos@email.com").get();
    Ingresso pista = ingressoService.getIngresso("Pista").get();
    Ingresso vip = ingressoService.getIngresso("VIP").get();
    Ingresso camarote = ingressoService.getIngresso("Camarote").get();

    // ============================================
    // 4. COMPRA 1: Ana Souza Silva
    // ============================================
    Compra compra1 = controller.iniciarNovaCompra(cliente1);
    controller.adicionarItemNaCompra(compra1, pista, 2); // 2 Pista
    controller.adicionarItemNaCompra(compra1, vip, 1);  // 1 VIP
    controller.finalizarCompra(compra1);

    // ============================================
    // 5. COMPRA 2: Carlos Lima
    // ============================================
    Compra compra2 = controller.iniciarNovaCompra(cliente2);
    controller.adicionarItemNaCompra(compra2, camarote, 2); // 2 Camarote
    controller.adicionarItemNaCompra(compra2, pista, 3);    // 3 Pista
    controller.finalizarCompra(compra2);

        // ============================================
        // 6. RELATÓRIO DE VENDAS
        // ============================================
    controller.listarComprasRealizadas();

    System.out.println("\n=== RESUMO FINANCEIRO ===");
    System.out.printf("Total de vendas: R$ %.2f\n", 
             controller.calcularTotalCompras());
                         
    System.out.println("\n--- Estoque atual Pista (deve ser 85) ---");
    System.out.println(ingressoService.getIngresso("Pista").get());


    System.out.println("\n╔════════════════════════════════════════╗");
    System.out.println("║       FIM DA DEMONSTRAÇÃO              ║");
    System.out.println("╚════════════════════════════════════════╝");
    }
}
