package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TelefoneE164Test {

    @Test
    void deveNormalizarNumeroBrasileiroComCodigoDoPais() {
        assertEquals("+5511944407845", TelefoneE164.normalizar("5511944407845"));
    }

    @Test
    void deveAdicionarCodigoDoBrasilQuandoAusente() {
        assertEquals("+5511944407845", TelefoneE164.normalizar("11944407845"));
    }

    @Test
    void deveManterMaisQuandoJaPresente() {
        assertEquals("+5511944407845", TelefoneE164.normalizar("+55 11 94440-7845"));
    }

    @Test
    void deveRemoverPrefixoCeroCero() {
        assertEquals("+5511944407845", TelefoneE164.normalizar("005511944407845"));
    }

    @Test
    void deveLancarQuandoVazio() {
        assertThrows(IllegalArgumentException.class, () -> TelefoneE164.normalizar("  "));
    }
}