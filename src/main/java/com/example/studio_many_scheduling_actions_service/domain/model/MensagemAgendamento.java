package com.example.studio_many_scheduling_actions_service.domain.model;

import java.time.LocalDateTime;

/**
 * Objeto de valor usado como conteudo da notificacao de mudanca de status.
 */
public class MensagemAgendamento {

    private final Long idAgendamento;
    private final StatusAgendamento acao;
    private final LocalDateTime dataAgendada;
    private final String telefone;

    public MensagemAgendamento(Long idAgendamento, StatusAgendamento acao, LocalDateTime dataAgendada, String telefone) {
        this.idAgendamento = idAgendamento;
        this.acao = acao;
        this.dataAgendada = dataAgendada;
        this.telefone = telefone;
    }

    public Long getIdAgendamento() {
        return idAgendamento;
    }

    public StatusAgendamento getAcao() {
        return acao;
    }

    public LocalDateTime getDataAgendada() {
        return dataAgendada;
    }

    public String getTelefone() {
        return telefone;
    }
}