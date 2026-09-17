package com.example.studio_many_scheduling_actions_service.presentation.dto;

import com.example.studio_many_scheduling_actions_service.application.dto.RespostaAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;

/**
 * Resposta da atualizacao de status do agendamento.
 */
public record ApiRespostaAcaoAgendamento(
        Long idAgendamento,
        StatusAgendamento statusAnterior,
        StatusAgendamento novoStatus,
        String mensagem) {

    public static ApiRespostaAcaoAgendamento criarDeRespostaAplicacao(RespostaAcaoAgendamento resposta) {
        return new ApiRespostaAcaoAgendamento(
                resposta.idAgendamento(),
                resposta.statusAnterior(),
                resposta.novoStatus(),
                resposta.mensagem());
    }
}