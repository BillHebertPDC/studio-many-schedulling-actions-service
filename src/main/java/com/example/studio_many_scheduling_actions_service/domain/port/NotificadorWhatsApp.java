package com.example.studio_many_scheduling_actions_service.domain.port;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;

public interface NotificadorWhatsApp {

    void enviarMensagemStatus(MensagemAgendamento mensagem);
}