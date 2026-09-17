package com.example.studio_many_scheduling_actions_service.application.service;

import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoAgendamentoNaoEncontrado;
import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoTransicaoStatusInvalida;
import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.GerenciadorTransacao;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.ValidadorTransicao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServicoTransicaoAgendamentoTest {

    private RepositorioAgendamento repositorio;
    private ValidadorTransicao validadorTransicao;
    private GerenciadorTransacao gerenciadorTransacao;
    private ServicoTransicaoAgendamento servico;

    @BeforeEach
    void configurar() {
        repositorio = mock(RepositorioAgendamento.class);
        validadorTransicao = mock(ValidadorTransicao.class);
        gerenciadorTransacao = mock(GerenciadorTransacao.class);

        servico = new ServicoTransicaoAgendamento(repositorio, validadorTransicao, gerenciadorTransacao);

        when(gerenciadorTransacao.executar(any())).thenAnswer(invocation -> {
            Supplier<?> acao = invocation.getArgument(0);
            return acao.get();
        });
        when(repositorio.salvar(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private Agendamento agendamento(StatusAgendamento status) {
        return new Agendamento(1L, 100L, 200L, LocalDateTime.of(2026, 9, 15, 8, 0), status, "5511944407845");
    }

    @Test
    void deveTransicionarEPersistir() {
        Agendamento agendamento = agendamento(StatusAgendamento.AGUARDANDO_SINAL);
        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(agendamento));
        when(validadorTransicao.transicaoValida(StatusAgendamento.AGUARDANDO_SINAL, StatusAgendamento.CHECKIN))
                .thenReturn(true);

        ServicoTransicaoAgendamento.ResultadoTransicao resultado = servico.aplicar(1L, StatusAgendamento.CHECKIN);

        verify(repositorio).salvar(agendamento);
        assertEquals(StatusAgendamento.CHECKIN, resultado.agendamento().getStatus());
        assertEquals(StatusAgendamento.AGUARDANDO_SINAL, resultado.statusAnterior());
    }

    @Test
    void deveLancarQuandoAgendamentoNaoEncontrado() {
        when(repositorio.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(ExcecaoAgendamentoNaoEncontrado.class,
                () -> servico.aplicar(1L, StatusAgendamento.CHECKIN));

        verify(repositorio, never()).salvar(any());
    }

    @Test
    void deveLancarQuandoTransicaoInvalida() {
        Agendamento agendamento = agendamento(StatusAgendamento.AGUARDANDO_SINAL);
        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(agendamento));
        when(validadorTransicao.transicaoValida(StatusAgendamento.AGUARDANDO_SINAL, StatusAgendamento.CHECKIN))
                .thenReturn(false);

        assertThrows(ExcecaoTransicaoStatusInvalida.class,
                () -> servico.aplicar(1L, StatusAgendamento.CHECKIN));

        verify(repositorio, never()).salvar(any());
        assertEquals(StatusAgendamento.AGUARDANDO_SINAL, agendamento.getStatus());
    }
}