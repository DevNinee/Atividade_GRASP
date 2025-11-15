package com.example;

import com.example.model.Cliente;
import com.example.model.Compra;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CompraValidationTest {

    @Test
    public void finalizeEmptyPurchaseThrows() {
        Compra compra = new Compra("T000", new Cliente("X","x@x.com"));
        assertThrows(IllegalStateException.class, () -> compra.finalizarCompra());
    }
}
