package com.example.studio_many_scheduling_actions_service.domain.exception;

public class ExcecaoAgendamentoNaoEncontrado extends RuntimeException {

    public ExcecaoAgendamentoNaoEncontrado(Long id) {
        super("Agendamento nao encontrado com id: " + id);
    }
}