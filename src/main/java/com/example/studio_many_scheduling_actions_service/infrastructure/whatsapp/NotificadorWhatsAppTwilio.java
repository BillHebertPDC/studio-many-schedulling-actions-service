package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.NotificadorWhatsApp;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Envia a mensagem de WhatsApp via Twilio (WhatsApp Business API).
 * Requer o provedor "twilio" em whatsapp.provider.
 */
@Component
@ConditionalOnProperty(name = "whatsapp.provider", havingValue = "twilio", matchIfMissing = false)
public class NotificadorWhatsAppTwilio implements NotificadorWhatsApp {

    private static final Logger log = LoggerFactory.getLogger(NotificadorWhatsAppTwilio.class);

    private static final String PREFIXO_WHATSAPP = "whatsapp:";

    private final FormatadorMensagemWaha formatador;
    private final String accountSid;
    private final String authToken;
    private final String apiKeySid;
    private final String whatsappNumber;

    public NotificadorWhatsAppTwilio(FormatadorMensagemWaha formatador,
                                     @Value("${twilio.account-sid}") String accountSid,
                                     @Value("${twilio.auth-token}") String authToken,
                                     @Value("${twilio.api-key-sid:}") String apiKeySid,
                                     @Value("${twilio.whatsapp-number}") String whatsappNumber) {
        this.formatador = formatador;
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.apiKeySid = apiKeySid;
        this.whatsappNumber = whatsappNumber;
    }

    @PostConstruct
    public void inicializar() {
        if (apiKeySid != null && !apiKeySid.isBlank()) {
            Twilio.init(apiKeySid, authToken, accountSid);
        } else {
            Twilio.init(accountSid, authToken);
        }
    }

    @Override
    public void enviarMensagemStatus(MensagemAgendamento mensagem) {
        String telefone = mensagem.getTelefone();
        if (telefone == null || telefone.isBlank()) {
            log.warn("Sem telefone para enviar mensagem de status {} idAgendamento={}",
                    mensagem.getAcao(), mensagem.getIdAgendamento());
            return;
        }

        String texto = formatador.formatar(mensagem);
        String telefoneE164 = TelefoneE164.normalizar(telefone);

        log.info("Enviando WhatsApp via Twilio: para={}, status={}", telefoneE164, mensagem.getAcao());

        try {
            Message.creator(
                    new PhoneNumber(PREFIXO_WHATSAPP + telefoneE164),
                    new PhoneNumber(whatsappNumber),
                    texto
            ).create();
            log.info("WhatsApp enviado com sucesso: idAgendamento={}, status={}", mensagem.getIdAgendamento(), mensagem.getAcao());
        } catch (Exception ex) {
            log.error("Falha ao enviar WhatsApp via Twilio: idAgendamento={}, status={}, erro={}",
                    mensagem.getIdAgendamento(), mensagem.getAcao(), ex.getMessage());
        }
    }
}