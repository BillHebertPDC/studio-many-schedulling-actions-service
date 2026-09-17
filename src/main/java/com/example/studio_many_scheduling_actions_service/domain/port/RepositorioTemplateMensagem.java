package com.example.studio_many_scheduling_actions_service.domain.port;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;

import java.util.Optional;

/**
 * Porta de acesso aos templates de mensagem (editaveis pelo banco).
 */
public interface RepositorioTemplateMensagem {

    Optional<String> buscarTemplate(StatusAgendamento status);
}