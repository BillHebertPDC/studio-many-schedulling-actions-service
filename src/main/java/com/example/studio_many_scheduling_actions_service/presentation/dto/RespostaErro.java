package com.example.studio_many_scheduling_actions_service.presentation.dto;

import java.time.LocalDateTime;

/**
 * Resposta estruturada de erro.
 */
public record RespostaErro(int status, String mensagem, LocalDateTime dataHora) {

    public static RespostaErro criar(int status, String mensagem) {
        return new RespostaErro(status, mensagem, LocalDateTime.now());
    }
}