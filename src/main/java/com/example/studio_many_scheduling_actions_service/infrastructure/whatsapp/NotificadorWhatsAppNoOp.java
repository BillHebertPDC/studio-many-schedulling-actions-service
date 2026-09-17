package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.NotificadorWhatsApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "whatsapp.provider", havingValue = "noop", matchIfMissing = false)
public class NotificadorWhatsAppNoOp implements NotificadorWhatsApp {

    private static final Logger log = LoggerFactory.getLogger(NotificadorWhatsAppNoOp.class);

    @Override
    public void enviarMensagemStatus(MensagemAgendamento mensagem) {
        log.info("[POC - WAHA desabilitado] Mensagem NAO enviada: status={}, idAgendamento={}",
                mensagem.getAcao(), mensagem.getIdAgendamento());
    }
}