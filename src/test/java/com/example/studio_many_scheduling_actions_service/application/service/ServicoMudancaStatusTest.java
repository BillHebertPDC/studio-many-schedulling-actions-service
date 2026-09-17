package com.example.studio_many_scheduling_actions_service.application.service;

import com.example.studio_many_scheduling_actions_service.application.dto.RequisicaoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.application.dto.RespostaAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServicoMudancaStatusTest {

    @Test
    void deveOrquestrarTransicaoENotificacao() {
        ServicoTransicaoAgendamento servicoTransicao = mock(ServicoTransicaoAgendamento.class);
        ServicoNotificacaoStatus servicoNotificacao = mock(ServicoNotificacaoStatus.class);
        ServicoMudancaStatus servico = new ServicoMudancaStatus(servicoTransicao, servicoNotificacao);

        Agendamento agendamento = new Agendamento(1L, 100L, 200L, LocalDateTime.of(2026, 9, 15, 8, 0), StatusAgendamento.CHECKIN, "5511944407845");
        ServicoTransicaoAgendamento.ResultadoTransicao resultado =
                new ServicoTransicaoAgendamento.ResultadoTransicao(agendamento, StatusAgendamento.AGUARDANDO_SINAL);
        when(servicoTransicao.aplicar(1L, StatusAgendamento.CHECKIN)).thenReturn(resultado);

        RespostaAcaoAgendamento resposta = servico.executarAcao(1L, new RequisicaoAcaoAgendamento(StatusAgendamento.CHECKIN));

        verify(servicoNotificacao).notificar(agendamento);
        assertEquals(1L, resposta.idAgendamento());
        assertEquals(StatusAgendamento.AGUARDANDO_SINAL, resposta.statusAnterior());
        assertEquals(StatusAgendamento.CHECKIN, resposta.novoStatus());
    }
}