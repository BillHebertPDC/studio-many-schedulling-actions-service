package com.example.studio_many_scheduling_actions_service.domain.exception;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;

public class ExcecaoTransicaoStatusInvalida extends RuntimeException {

    public ExcecaoTransicaoStatusInvalida(StatusAgendamento atual, StatusAgendamento alvo) {
        super("Transicao de status invalida: " + atual + " -> " + alvo);
    }
}