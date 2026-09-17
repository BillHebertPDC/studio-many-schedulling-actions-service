package com.example.studio_many_scheduling_actions_service.domain.port;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;

public interface ValidadorTransicao {

    boolean transicaoValida(StatusAgendamento de, StatusAgendamento para);
}