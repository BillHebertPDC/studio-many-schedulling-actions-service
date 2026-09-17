package com.example.studio_many_scheduling_actions_service.domain.model;

import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoTransicaoStatusInvalida;
import com.example.studio_many_scheduling_actions_service.domain.port.ValidadorTransicao;

import java.time.LocalDateTime;

/**
 * Agregado de agendamento. Objeto puro de dominio, sem dependencia de
 * infraestrutura (Spring/JDBC). As regras de mudanca de status vivem aqui.
 */
public class Agendamento {

    private Long id;
    private final Long idCliente;
    private final Long idProfissional;
    private LocalDateTime dataAgendada;
    private StatusAgendamento status;
    private String telefoneCliente;

    public Agendamento(Long id, Long idCliente, Long idProfissional, LocalDateTime dataAgendada, StatusAgendamento status, String telefoneCliente) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProfissional = idProfissional;
        this.dataAgendada = dataAgendada;
        this.status = status;
        this.telefoneCliente = telefoneCliente;
    }

    public void transicionarPara(StatusAgendamento alvo, ValidadorTransicao validador) {
        if (!validador.transicaoValida(status, alvo)) {
            throw new ExcecaoTransicaoStatusInvalida(status, alvo);
        }
        this.status = alvo;
    }

    public Long getId() {
        return id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public Long getIdProfissional() {
        return idProfissional;
    }

    public LocalDateTime getDataAgendada() {
        return dataAgendada;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }
}