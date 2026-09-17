package com.example.studio_many_scheduling_actions_service.application.dto;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;

public record RespostaAcaoAgendamento(
        Long idAgendamento,
        StatusAgendamento statusAnterior,
        StatusAgendamento novoStatus,
        String mensagem) {
}