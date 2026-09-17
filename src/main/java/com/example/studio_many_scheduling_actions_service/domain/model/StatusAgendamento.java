package com.example.studio_many_scheduling_actions_service.domain.model;

public enum StatusAgendamento {

    AGUARDANDO_SINAL("Aguardando Sinal"),
    CONFIRMADO_CLIENTE("Confirmado Cliente"),
    EM_ESPERA_PROFISSIONAL("Em Espera Profissional"),
    CONFIRMADO_PROFISSIONAL("Confirmado Profissional"),
    CONFIRMADO_REAGENDAMENTO_CLIENTE("Confirmado Reagendamento Cliente"),
    CONFIRMADO_REAGENDAMENTO_PROFISSIONAL("Confirmado Reagendamento Profissional"),
    RECUSADO_REAGENDAMENTO_CLIENTE("Recusado Reagendamento Cliente"),
    RECUSADO_REAGENDAMENTO_PROFISSIONAL("Recusado Reagendamento Profissional"),
    REAGENDAR_CLIENTE("Reagendar Cliente"),
    REAGENDAR_PROFISSIONAL("Reagendar Profissional"),
    RECUSADO_REVALIDAR("Recusado Revalidar"),
    CHECKIN("Checkin"),
    EM_ATENDIMENTO("Em Atendimento"),
    CONCLUIDO("Concluido"),
    POS_ATENDIMENTO("Pos Atendimento"),
    NO_SHOW("No Show"),
    CANCELADO_CLIENTE("Cancelado Cliente"),
    CANCELADO_PROFISSIONAL("Cancelado Profissional"),
    CANCELADO_AUTOMATICAMENTE("Cancelado Automaticamente"),
    RECUSADO_CLIENTE("Recusado Cliente");

    private final String descricao;

    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }

    public String obterDescricao() {
        return descricao;
    }
}