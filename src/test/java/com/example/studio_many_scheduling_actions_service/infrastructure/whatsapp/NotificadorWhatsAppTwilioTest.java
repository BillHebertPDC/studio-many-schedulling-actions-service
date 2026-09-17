package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioTemplateMensagem;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotificadorWhatsAppTwilioTest {

    private NotificadorWhatsAppTwilio notificador;

    @BeforeEach
    void configurar() {
        RepositorioTemplateMensagem repositorio = mock(RepositorioTemplateMensagem.class);
        when(repositorio.buscarTemplate(StatusAgendamento.CHECKIN))
                .thenReturn(Optional.of("Olá! Você fez *check-in* para o agendamento de *%s*. Logo iniciaremos o atendimento."));

        FormatadorMensagemWaha formatador = new FormatadorMensagemWaha(repositorio);
        notificador = new NotificadorWhatsAppTwilio(formatador, "ACconta", "token", "", "whatsapp:+14155238886");
    }

    private MensagemAgendamento mensagemCheckin() {
        return new MensagemAgendamento(
                1L,
                StatusAgendamento.CHECKIN,
                LocalDateTime.of(2026, 9, 15, 8, 0),
                "5511944407845"
        );
    }

    @Test
    void deveEnviarMensagemComTelefoneNormalizadoParaE164() {
        try (MockedStatic<Message> mock = mockStatic(Message.class)) {
            mock.when(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString()))
                    .thenAnswer(invocacao -> {
                        PhoneNumber para = invocacao.getArgument(0);
                        PhoneNumber de = invocacao.getArgument(1);
                        String corpo = invocacao.getArgument(2);

                        assertEquals("whatsapp:+5511944407845", para.toString());
                        assertEquals("whatsapp:+14155238886", de.toString());
                        assertTrue(corpo.contains("check-in"));

                        return mock(MessageCreator.class);
                    });

            notificador.enviarMensagemStatus(mensagemCheckin());

            mock.verify(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString()));
        }
    }

    @Test
    void naoDeveChamarTwilioQuandoSemTelefone() {
        MensagemAgendamento mensagem = new MensagemAgendamento(
                1L,
                StatusAgendamento.CHECKIN,
                LocalDateTime.of(2026, 9, 15, 8, 0),
                "  "
        );

        try (MockedStatic<Message> mock = mockStatic(Message.class)) {
            notificador.enviarMensagemStatus(mensagem);
            mock.verifyNoInteractions();
        }
    }
}