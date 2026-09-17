package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioTemplateMensagem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FormatadorMensagemWahaTest {

    @Test
    void deveFormatarUsandoTemplateDoRepositorio() {
        RepositorioTemplateMensagem repositorio = mock(RepositorioTemplateMensagem.class);
        when(repositorio.buscarTemplate(StatusAgendamento.CONFIRMADO_CLIENTE))
                .thenReturn(Optional.of("Olá! Seu agendamento foi *confirmado* para *%s*. Qualquer dúvida, estamos à disposição."));

        FormatadorMensagemWaha formatador = new FormatadorMensagemWaha(repositorio);
        MensagemAgendamento mensagem = new MensagemAgendamento(
                1L,
                StatusAgendamento.CONFIRMADO_CLIENTE,
                LocalDateTime.of(2026, 9, 15, 14, 30),
                "5511944407845"
        );

        String texto = formatador.formatar(mensagem);

        assertThat(texto)
                .contains("confirmado")
                .contains("15/09/2026 14:30");
    }

    @Test
    void deveUsarTemplatePadraoQuandoNaoEncontrado() {
        RepositorioTemplateMensagem repositorio = mock(RepositorioTemplateMensagem.class);
        when(repositorio.buscarTemplate(StatusAgendamento.NO_SHOW)).thenReturn(Optional.empty());

        FormatadorMensagemWaha formatador = new FormatadorMensagemWaha(repositorio);
        MensagemAgendamento mensagem = new MensagemAgendamento(
                1L,
                StatusAgendamento.NO_SHOW,
                null,
                "5511944407845"
        );

        String texto = formatador.formatar(mensagem);

        assertThat(texto)
                .contains("O status do seu agendamento mudou para")
                .contains("o horário previamente informado");
    }
}