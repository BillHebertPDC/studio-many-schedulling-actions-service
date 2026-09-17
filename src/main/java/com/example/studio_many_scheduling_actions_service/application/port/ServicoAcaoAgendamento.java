package com.example.studio_many_scheduling_actions_service.application.port;

import com.example.studio_many_scheduling_actions_service.application.dto.RequisicaoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.application.dto.RespostaAcaoAgendamento;

/**
 * Porta de entrada do caso de uso de mudanca de status.
 */
public interface ServicoAcaoAgendamento {

    RespostaAcaoAgendamento executarAcao(Long idAgendamento, RequisicaoAcaoAgendamento requisicao);
}