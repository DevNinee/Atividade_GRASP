package com.example.view;

import com.example.controller.CompraController;
import com.example.model.Compra;
import com.example.model.Ingresso;
import com.example.model.ItemCompra;
import java.util.List;

/**
 * Classe de interface (View) para exibir informações e interagir com usuário.
 */
public class CompraView {
    private CompraController controller;

    public CompraView(CompraController controller) {
        this.controller = controller;
    }

    public void exibirCabecalho() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE VENDAS DE INGRESSOS        ║");
        System.out.println("║     Aplicando Padrões GRASP            ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    public void exibirIngressosDisponiveis(Ingresso[] ingressos) {
        System.out.println("\n--- INGRESSOS DISPONÍVEIS ---");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }

    public void exibirNovaCompraIniciada(Compra compra) {
        System.out.println("\n=== NOVA COMPRA INICIADA ===");
        System.out.println("Número: " + compra.getCodigoCompra());
        System.out.println("Cliente: " + compra.getCliente().getNome());
    }

    public void exibirItemAdicionado(ItemCompra item) {
        System.out.println("Item adicionado: " + item);
    }

    public void exibirErro(String mensagem) {
        System.out.println(" ERRO: " + mensagem);
    }

    public void exibirSucesso(String mensagem) {
        System.out.println("" + mensagem);
    }

    public void exibirCompraFinalizada(Compra compra) {
        System.out.println("\n=== COMPRA FINALIZADA ===");
        System.out.println(compra);
    }

    public void exibirComprasRealizadas() {
        System.out.println("\n=== COMPRAS REALIZADAS ===");
        List<Compra> compras = controller.listarComprasRealizadas();
        if (compras.isEmpty()) {
            System.out.println("Nenhuma compra realizada ainda.");
        } else {
            for (Compra c : compras) {
                System.out.println(c);
            }
        }
    }

    public void exibirResumoFinanceiro() {
        System.out.println("\n=== RESUMO FINANCEIRO ===");
        System.out.printf("Total de vendas: R$ %.2f\n", controller.calcularTotalCompras());
    }

    public void exibirRodape() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       FIM DA DEMONSTRAÇÃO              ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}
