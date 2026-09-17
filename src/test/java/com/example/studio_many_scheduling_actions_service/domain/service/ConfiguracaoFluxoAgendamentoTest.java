package com.example.studio_many_scheduling_actions_service.domain.service;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.ValidadorTransicao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfiguracaoFluxoAgendamentoTest {

    private final ValidadorTransicao validador = new ConfiguracaoFluxoAgendamento();

    @Test
    void devePermitirTransicoesValidas() {
        assertTrue(validador.transicaoValida(StatusAgendamento.AGUARDANDO_SINAL, StatusAgendamento.CONFIRMADO_CLIENTE));
        assertTrue(validador.transicaoValida(StatusAgendamento.AGUARDANDO_SINAL, StatusAgendamento.CANCELADO_CLIENTE));
        assertTrue(validador.transicaoValida(StatusAgendamento.AGUARDANDO_SINAL, StatusAgendamento.CANCELADO_AUTOMATICAMENTE));
        assertTrue(validador.transicaoValida(StatusAgendamento.CONFIRMADO_CLIENTE, StatusAgendamento.EM_ESPERA_PROFISSIONAL));
        assertTrue(validador.transicaoValida(StatusAgendamento.EM_ESPERA_PROFISSIONAL, StatusAgendamento.CONFIRMADO_PROFISSIONAL));
        assertTrue(validador.transicaoValida(StatusAgendamento.CONFIRMADO_PROFISSIONAL, StatusAgendamento.CHECKIN));
        assertTrue(validador.transicaoValida(StatusAgendamento.CHECKIN, StatusAgendamento.EM_ATENDIMENTO));
        assertTrue(validador.transicaoValida(StatusAgendamento.EM_ATENDIMENTO, StatusAgendamento.CONCLUIDO));
        assertTrue(validador.transicaoValida(StatusAgendamento.CONCLUIDO, StatusAgendamento.POS_ATENDIMENTO));
        assertTrue(validador.transicaoValida(StatusAgendamento.REAGENDAR_PROFISSIONAL, StatusAgendamento.CONFIRMADO_REAGENDAMENTO_CLIENTE));
    }

    @Test
    void deveRejeitarTransicoesInvalidas() {
        assertFalse(validador.transicaoValida(StatusAgendamento.AGUARDANDO_SINAL, StatusAgendamento.CHECKIN));
        assertFalse(validador.transicaoValida(StatusAgendamento.AGUARDANDO_SINAL, StatusAgendamento.AGUARDANDO_SINAL));
        assertFalse(validador.transicaoValida(StatusAgendamento.POS_ATENDIMENTO, StatusAgendamento.AGUARDANDO_SINAL));
        assertFalse(validador.transicaoValida(StatusAgendamento.CONCLUIDO, StatusAgendamento.CHECKIN));
        assertFalse(validador.transicaoValida(StatusAgendamento.EM_ATENDIMENTO, StatusAgendamento.CONFIRMADO_CLIENTE));
    }
}