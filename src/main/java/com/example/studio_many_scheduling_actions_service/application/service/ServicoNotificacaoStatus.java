package com.example.studio_many_scheduling_actions_service.application.service;

import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.NotificadorWhatsApp;

/**
 * Monta e envia a notificacao de mudanca de status.
 * Responsabilidade unica: notificacao.
 */
public class ServicoNotificacaoStatus {

    private final NotificadorWhatsApp notificadorWhatsApp;

    public ServicoNotificacaoStatus(NotificadorWhatsApp notificadorWhatsApp) {
        this.notificadorWhatsApp = notificadorWhatsApp;
    }

    public void notificar(Agendamento agendamento) {
        MensagemAgendamento mensagem = new MensagemAgendamento(
                agendamento.getId(),
                agendamento.getStatus(),
                agendamento.getDataAgendada(),
                agendamento.getTelefoneCliente()
        );
        notificadorWhatsApp.enviarMensagemStatus(mensagem);
    }
}