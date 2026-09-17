package com.example.studio_many_scheduling_actions_service.domain.service;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.ValidadorTransicao;

import java.util.Map;
import java.util.Set;

import static com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento.*;
import static java.util.Map.entry;

public class ConfiguracaoFluxoAgendamento implements ValidadorTransicao {

    private static final Map<StatusAgendamento, Set<StatusAgendamento>> TRANSICOES = Map.ofEntries(
        entry(AGUARDANDO_SINAL, Set.of(
            CONFIRMADO_CLIENTE,
            CANCELADO_CLIENTE,
            CANCELADO_AUTOMATICAMENTE
        )),
        entry(CONFIRMADO_CLIENTE, Set.of(
            EM_ESPERA_PROFISSIONAL
        )),
        entry(EM_ESPERA_PROFISSIONAL, Set.of(
            CONFIRMADO_PROFISSIONAL,
            CANCELADO_CLIENTE,
            CANCELADO_PROFISSIONAL,
            RECUSADO_REVALIDAR
        )),
        entry(CONFIRMADO_PROFISSIONAL, Set.of(
            CHECKIN,
            NO_SHOW,
            REAGENDAR_CLIENTE,
            REAGENDAR_PROFISSIONAL
        )),
        entry(REAGENDAR_CLIENTE, Set.of(
            CONFIRMADO_REAGENDAMENTO_PROFISSIONAL,
            RECUSADO_REAGENDAMENTO_PROFISSIONAL,
            CANCELADO_CLIENTE,
            CANCELADO_PROFISSIONAL
        )),
        entry(REAGENDAR_PROFISSIONAL, Set.of(
            CONFIRMADO_REAGENDAMENTO_CLIENTE,
            RECUSADO_REAGENDAMENTO_CLIENTE,
            CANCELADO_CLIENTE,
            CANCELADO_PROFISSIONAL,
            RECUSADO_CLIENTE
        )),
        entry(CONFIRMADO_REAGENDAMENTO_CLIENTE, Set.of(
            CONFIRMADO_PROFISSIONAL
        )),
        entry(CONFIRMADO_REAGENDAMENTO_PROFISSIONAL, Set.of(
            CONFIRMADO_PROFISSIONAL
        )),
        entry(RECUSADO_REAGENDAMENTO_CLIENTE, Set.of(
            REAGENDAR_PROFISSIONAL
        )),
        entry(RECUSADO_REAGENDAMENTO_PROFISSIONAL, Set.of(
            REAGENDAR_CLIENTE
        )),
        entry(RECUSADO_REVALIDAR, Set.of(
            AGUARDANDO_SINAL
        )),
        entry(CHECKIN, Set.of(
            EM_ATENDIMENTO
        )),
        entry(EM_ATENDIMENTO, Set.of(
            CONCLUIDO,
            NO_SHOW
        )),
        entry(CONCLUIDO, Set.of(
            POS_ATENDIMENTO
        ))
    );

    @Override
    public boolean transicaoValida(StatusAgendamento de, StatusAgendamento para) {
        Set<StatusAgendamento> permitidas = TRANSICOES.get(de);
        return permitidas != null && permitidas.contains(para);
    }
}