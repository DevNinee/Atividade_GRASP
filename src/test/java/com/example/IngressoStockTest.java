package com.example;

import com.example.model.Cliente;
import com.example.model.Compra;
import com.example.model.Ingresso;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class IngressoStockTest {

    @Test
    public void insufficientStockThrows() {
        Ingresso ingresso = new Ingresso("VIP", 200.0, 1);
        Compra compra = new Compra("T001", new Cliente("Y","y@y.com"));
        assertThrows(IllegalStateException.class, () -> compra.adicionarItem(ingresso, 2));
    }
}
