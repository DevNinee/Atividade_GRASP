package app;

import controller.CompraController;
import model.Cliente;
import model.Compra;
import model.Ingresso;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main interativo para demonstrar o sistema de vendas.
 * Adaptado para entrada via console conforme solicitado.
 *
 * Padrões GRASP mencionados aqui (novamente):
 * - Controller: controller.CompraController continua sendo o coordenador das ações da aplicação.
 * - Creator: model.Compra.adicionarItem cria ItemCompra.
 * - Information Expert: model.ItemCompra.calcularSubtotal e model.Compra.calcularTotal e model.Ingresso.verificarDisponibilidade/vender.
 *
 * Esta versão permite criar clientes, iniciar compras, adicionar itens e finalizar via menu no console.
 */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        CompraController controller = new CompraController();

        // Pre-cadastro de ingressos (catálogo)
        Map<Integer, Ingresso> catalogo = new HashMap<>();
        catalogo.put(1, new Ingresso("Pista", new BigDecimal("120.00"), 100));
        catalogo.put(2, new Ingresso("VIP", new BigDecimal("220.00"), 40));
        catalogo.put(3, new Ingresso("Camarote", new BigDecimal("380.00"), 20));

        System.out.println("=== FackStore - Sistema de Vendas (Interativo) ===");

        Cliente clienteAtual = null;
        Compra compraAtual = null;

        while (true) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            String opc = SCANNER.nextLine().trim();

            switch (opc) {
                case "1": // listar catálogo
                    System.out.println("--- CATÁLOGO DE INGRESSOS ---");
                    for (Map.Entry<Integer, Ingresso> e : catalogo.entrySet()) {
                        System.out.println(e.getKey() + ") " + e.getValue());
                    }
                    break;
                case "2": // criar/selecionar cliente
                    System.out.print("Nome do cliente: ");
                    String nome = SCANNER.nextLine().trim();
                    System.out.print("Email do cliente: ");
                    String email = SCANNER.nextLine().trim();
                    clienteAtual = new Cliente(nome, email);
                    System.out.println("Cliente selecionado: " + clienteAtual);
                    break;
                case "3": // iniciar compra
                    if (clienteAtual == null) {
                        System.out.println("Crie/Selecione um cliente primeiro (opção 2).");
                        break;
                    }
                    compraAtual = controller.iniciarCompra(clienteAtual);
                    System.out.println("Compra iniciada: " + compraAtual.getCodigo());
                    break;
                case "4": // adicionar item
                    if (compraAtual == null) {
                        System.out.println("Inicie uma compra primeiro (opção 3).\n");
                        break;
                    }
                    System.out.print("Id do ingresso (ver catálogo): ");
                    String idStr = SCANNER.nextLine().trim();
                    int id;
                    try {
                        id = Integer.parseInt(idStr);
                    } catch (NumberFormatException ex) {
                        System.out.println("Id inválido.");
                        break;
                    }
                    Ingresso ingresso = catalogo.get(id);
                    if (ingresso == null) {
                        System.out.println("Ingresso não encontrado.");
                        break;
                    }
                    System.out.print("Quantidade: ");
                    String qtStr = SCANNER.nextLine().trim();
                    int qtd;
                    try {
                        qtd = Integer.parseInt(qtStr);
                    } catch (NumberFormatException ex) {
                        System.out.println("Quantidade inválida.");
                        break;
                    }
                    controller.adicionarItemNaCompra(compraAtual, ingresso, qtd);
                    break;
                case "5": // finalizar compra
                    if (compraAtual == null) {
                        System.out.println("Inicie uma compra primeiro (opção 3).\n");
                        break;
                    }
                    controller.finalizarCompra(compraAtual);
                    System.out.println("Total da compra: R$ " + compraAtual.calcularTotal());
                    // limpar compra atual para iniciar nova
                    compraAtual = null;
                    break;
                case "6": // listar compras
                    controller.listarCompras();
                    break;
                case "7": // total vendas
                    System.out.println("Total de vendas: R$ " + controller.calcularTotalVendas());
                    break;
                case "0":
                    System.out.println("Saindo... Obrigado.");
                    SCANNER.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("1) Listar catálogo de ingressos");
        System.out.println("2) Criar/Selecionar cliente");
        System.out.println("3) Iniciar nova compra");
        System.out.println("4) Adicionar item à compra");
        System.out.println("5) Finalizar compra");
        System.out.println("6) Listar compras registradas");
        System.out.println("7) Mostrar total de vendas");
        System.out.println("0) Sair");
    }
}