package com.example.studio_many_scheduling_actions_service.application.service;

import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.NotificadorWhatsApp;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ServicoNotificacaoStatusTest {

    @Test
    void deveMontarMensagemENotificar() {
        NotificadorWhatsApp notificador = mock(NotificadorWhatsApp.class);
        ServicoNotificacaoStatus servico = new ServicoNotificacaoStatus(notificador);

        Agendamento agendamento = new Agendamento(1L, 100L, 200L, LocalDateTime.of(2026, 9, 15, 8, 0), StatusAgendamento.CHECKIN, "5511944407845");

        servico.notificar(agendamento);

        ArgumentCaptor<MensagemAgendamento> captor = ArgumentCaptor.forClass(MensagemAgendamento.class);
        verify(notificador).enviarMensagemStatus(captor.capture());
        assertEquals(1L, captor.getValue().getIdAgendamento());
        assertEquals(StatusAgendamento.CHECKIN, captor.getValue().getAcao());
        assertEquals(agendamento.getDataAgendada(), captor.getValue().getDataAgendada());
        assertEquals("5511944407845", captor.getValue().getTelefone());
    }
}