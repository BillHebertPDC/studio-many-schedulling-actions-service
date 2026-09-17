package com.example.studio_many_scheduling_actions_service.presentation.dto;

import com.example.studio_many_scheduling_actions_service.application.dto.RequisicaoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;

/**
 * Requisicao para executar uma acao sobre o agendamento.
 */
public record ApiRequisicaoAcaoAgendamento(StatusAgendamento acao) {

    public RequisicaoAcaoAgendamento converterParaRequisicaoAplicacao() {
        return new RequisicaoAcaoAgendamento(acao);
    }
}