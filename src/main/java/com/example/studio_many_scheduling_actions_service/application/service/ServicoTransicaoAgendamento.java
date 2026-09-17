package com.example.studio_many_scheduling_actions_service.application.service;

import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoAgendamentoNaoEncontrado;
import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.GerenciadorTransacao;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.ValidadorTransicao;

/**
 * Aplica a transicao de status e persiste, tudo dentro de uma transacao.
 * Responsabilidade unica: transicao + persistencia.
 */
public class ServicoTransicaoAgendamento {

    private final RepositorioAgendamento repositorio;
    private final ValidadorTransicao validadorTransicao;
    private final GerenciadorTransacao gerenciadorTransacao;

    public ServicoTransicaoAgendamento(RepositorioAgendamento repositorio,
                                       ValidadorTransicao validadorTransicao,
                                       GerenciadorTransacao gerenciadorTransacao) {
        this.repositorio = repositorio;
        this.validadorTransicao = validadorTransicao;
        this.gerenciadorTransacao = gerenciadorTransacao;
    }

    public ResultadoTransicao aplicar(Long idAgendamento, StatusAgendamento statusAlvo) {
        return gerenciadorTransacao.executar(() -> {
            Agendamento agendamento = repositorio.buscarPorId(idAgendamento)
                    .orElseThrow(() -> new ExcecaoAgendamentoNaoEncontrado(idAgendamento));

            StatusAgendamento statusAnterior = agendamento.getStatus();
            agendamento.transicionarPara(statusAlvo, validadorTransicao);

            Agendamento atualizado = repositorio.salvar(agendamento);
            return new ResultadoTransicao(atualizado, statusAnterior);
        });
    }

    public record ResultadoTransicao(Agendamento agendamento, StatusAgendamento statusAnterior) {
    }
}