package com.example.studio_many_scheduling_actions_service.application.service;

import com.example.studio_many_scheduling_actions_service.application.dto.RequisicaoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.application.dto.RespostaAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.application.port.ServicoAcaoAgendamento;

/**
 * Caso de uso de mudanca de status: orquestra a transicao (persistida em
 * transacao) e, apos isso, a notificacao. Responsabilidade unica: orquestracao.
 */
public class ServicoMudancaStatus implements ServicoAcaoAgendamento {

    private final ServicoTransicaoAgendamento servicoTransicao;
    private final ServicoNotificacaoStatus servicoNotificacao;

    public ServicoMudancaStatus(ServicoTransicaoAgendamento servicoTransicao,
                                ServicoNotificacaoStatus servicoNotificacao) {
        this.servicoTransicao = servicoTransicao;
        this.servicoNotificacao = servicoNotificacao;
    }

    @Override
    public RespostaAcaoAgendamento executarAcao(Long idAgendamento, RequisicaoAcaoAgendamento requisicao) {
        ServicoTransicaoAgendamento.ResultadoTransicao resultado =
                servicoTransicao.aplicar(idAgendamento, requisicao.acao());

        servicoNotificacao.notificar(resultado.agendamento());

        return new RespostaAcaoAgendamento(
                resultado.agendamento().getId(),
                resultado.statusAnterior(),
                resultado.agendamento().getStatus(),
                "Agendamento atualizado para " + resultado.agendamento().getStatus().obterDescricao()
        );
    }
}