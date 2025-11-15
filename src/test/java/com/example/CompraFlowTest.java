package com.example;

import com.example.controller.CompraController;
import com.example.model.Cliente;
import com.example.model.Compra;
import com.example.model.Ingresso;
import com.example.service.ClienteService;
import com.example.service.IngressoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompraFlowTest {

    @Test
    public void successfulPurchaseFlow() {
        IngressoService ingressoService = new IngressoService();
        ClienteService clienteService = new ClienteService();
        CompraController controller = new CompraController();

        ingressoService.criarIngresso("Pista", 50.0, 10);
        clienteService.criarCliente("Teste", "teste@ex.com");

        Cliente cliente = clienteService.getCliente("teste@ex.com").orElseThrow();
        Ingresso ingresso = ingressoService.getIngresso("Pista").orElseThrow();

        Compra compra = controller.iniciarNovaCompra(cliente);
        controller.adicionarItemNaCompra(compra, ingresso, 2);
        controller.finalizarCompra(compra);

        assertEquals("Finalizada", compra.getStatus());
        assertEquals(100.0, compra.calcularTotal(), 0.0001);
        assertEquals(8, ingresso.getQuantidadeDisponivel());
    }
}
