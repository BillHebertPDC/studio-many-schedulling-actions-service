package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioTemplateMensagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class NotificadorWhatsAppWahaTest {

    private MockRestServiceServer servidor;
    private NotificadorWhatsAppWaha notificador;

    @BeforeEach
    void configurar() {
        RestClient.Builder construtor = RestClient.builder();
        servidor = MockRestServiceServer.bindTo(construtor).build();

        RepositorioTemplateMensagem repositorio = mock(RepositorioTemplateMensagem.class);
        when(repositorio.buscarTemplate(StatusAgendamento.CHECKIN))
                .thenReturn(Optional.of("Olá! Você fez *check-in* para o agendamento de *%s*. Logo iniciaremos o atendimento."));

        notificador = new NotificadorWhatsAppWaha(
                construtor,
                new FormatadorMensagemWaha(repositorio),
                "http://localhost:3000",
                "minha-chave-api",
                "padrao",
                "5511999999999"
        );
    }

    @Test
    void deveEnviarTextoParaApiDoWaha() {
        MensagemAgendamento mensagem = new MensagemAgendamento(
                1L,
                StatusAgendamento.CHECKIN,
                LocalDateTime.of(2026, 9, 15, 8, 0),
                "5511944407845"
        );

        servidor.expect(requestTo("http://localhost:3000/api/sendText"))
                .andExpect(method(POST))
                .andExpect(header("X-Api-Key", "minha-chave-api"))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.session").value("padrao"))
                .andExpect(jsonPath("$.chatId").value("5511944407845@c.us"))
                .andExpect(jsonPath("$.text").value("Olá! Você fez *check-in* para o agendamento de *15/09/2026 08:00*. Logo iniciaremos o atendimento."))
                .andRespond(withSuccess());

        notificador.enviarMensagemStatus(mensagem);

        servidor.verify();
    }
}