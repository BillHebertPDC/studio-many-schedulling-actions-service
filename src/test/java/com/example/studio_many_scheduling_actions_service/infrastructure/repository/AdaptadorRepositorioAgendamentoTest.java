package com.example.studio_many_scheduling_actions_service.infrastructure.repository;

import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest(properties = "spring.sql.init.mode=always")
@Import(AdaptadorRepositorioAgendamento.class)
class AdaptadorRepositorioAgendamentoTest {

    @Autowired
    private AdaptadorRepositorioAgendamento adaptador;

    private Agendamento novoAgendamento() {
        return new Agendamento(null, 100L, 200L, LocalDateTime.of(2026, 9, 15, 8, 0), StatusAgendamento.AGUARDANDO_SINAL, "5511944407845");
    }

    @Test
    void deveSalvarEBuscarPorId() {
        Agendamento criado = adaptador.salvar(novoAgendamento());

        assertNotNull(criado.getId());

        Optional<Agendamento> encontrado = adaptador.buscarPorId(criado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(100L, encontrado.get().getIdCliente());
        assertEquals(200L, encontrado.get().getIdProfissional());
        assertEquals(StatusAgendamento.AGUARDANDO_SINAL, encontrado.get().getStatus());
        assertEquals("5511944407845", encontrado.get().getTelefoneCliente());
        assertEquals(criado.getDataAgendada(), encontrado.get().getDataAgendada());
    }

    @Test
    void deveAtualizarStatus() {
        Agendamento criado = adaptador.salvar(novoAgendamento());

        Agendamento atualizado = adaptador.salvar(new Agendamento(
                criado.getId(),
                100L,
                200L,
                LocalDateTime.of(2026, 9, 15, 8, 0),
                StatusAgendamento.CONFIRMADO_CLIENTE,
                "5511944407845"
        ));

        assertEquals(StatusAgendamento.CONFIRMADO_CLIENTE, atualizado.getStatus());
        assertEquals(StatusAgendamento.CONFIRMADO_CLIENTE,
                adaptador.buscarPorId(criado.getId()).orElseThrow().getStatus());
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrado() {
        assertTrue(adaptador.buscarPorId(999L).isEmpty());
    }
}