package com.example.studio_many_scheduling_actions_service.domain.port;

import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;

import java.util.Optional;

public interface RepositorioAgendamento {

    Optional<Agendamento> buscarPorId(Long id);

    Agendamento salvar(Agendamento agendamento);
}