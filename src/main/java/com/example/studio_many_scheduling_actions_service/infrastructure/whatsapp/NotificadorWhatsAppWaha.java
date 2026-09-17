package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.NotificadorWhatsApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

/**
 * Envia a mensagem de WhatsApp via WAHA (endpoint /api/sendText).
 */
@Component
@ConditionalOnProperty(name = "whatsapp.provider", havingValue = "waha", matchIfMissing = true)
public class NotificadorWhatsAppWaha implements NotificadorWhatsApp {

    private static final Logger log = LoggerFactory.getLogger(NotificadorWhatsAppWaha.class);

    private static final String SUFIXO_CHAT_ID = "@c.us";

    private final RestClient restClient;
    private final FormatadorMensagemWaha formatador;
    private final String chaveApi;
    private final String sessao;
    private final String telefonePadrao;

    public NotificadorWhatsAppWaha(RestClient.Builder construtorRestClient,
                                   FormatadorMensagemWaha formatador,
                                   @Value("${waha.api.url}") String urlApi,
                                   @Value("${waha.api.apikey:}") String chaveApi,
                                   @Value("${waha.session:Teste}") String sessao,
                                   @Value("${waha.phone.default:}") String telefonePadrao) {
        this.formatador = formatador;
        this.chaveApi = chaveApi;
        this.sessao = sessao;
        this.telefonePadrao = telefonePadrao;
        this.restClient = construtorRestClient
                .baseUrl(urlApi)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public void enviarMensagemStatus(MensagemAgendamento mensagem) {
        String telefone = (mensagem.getTelefone() == null || mensagem.getTelefone().isBlank())
                ? telefonePadrao
                : mensagem.getTelefone();

        if (telefone == null || telefone.isBlank()) {
            log.warn("Sem telefone para enviar mensagem de status {} idAgendamento={}",
                    mensagem.getAcao(), mensagem.getIdAgendamento());
            return;
        }

        String texto = formatador.formatar(mensagem);
        String chatId = telefone + SUFIXO_CHAT_ID;

        log.info("Enviando WhatsApp via WAHA: sessao={}, chatId={}, status={}",
                sessao, chatId, mensagem.getAcao());

        try {
            RestClient.RequestBodySpec requisicao = restClient.post().uri("/api/sendText");
            if (chaveApi != null && !chaveApi.isBlank()) {
                requisicao.header("X-Api-Key", chaveApi);
            }
            requisicao.body(Map.of("session", sessao, "chatId", chatId, "text", texto))
                    .retrieve()
                    .toBodilessEntity();
            log.info("WhatsApp enviado com sucesso: idAgendamento={}, status={}", mensagem.getIdAgendamento(), mensagem.getAcao());
        } catch (Exception ex) {
            log.error("Falha ao enviar WhatsApp via WAHA: idAgendamento={}, status={}, erro={}",
                    mensagem.getIdAgendamento(), mensagem.getAcao(), ex.getMessage());
        }
    }
}