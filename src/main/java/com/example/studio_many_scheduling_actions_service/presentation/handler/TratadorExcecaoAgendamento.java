package com.example.studio_many_scheduling_actions_service.presentation.handler;

import com.example.studio_many_scheduling_actions_service.presentation.dto.RespostaErro;
import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoTransicaoStatusInvalida;
import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoAgendamentoNaoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorExcecaoAgendamento {

    @ExceptionHandler(ExcecaoAgendamentoNaoEncontrado.class)
    public ResponseEntity<RespostaErro> tratarAgendamentoNaoEncontrado(ExcecaoAgendamentoNaoEncontrado ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RespostaErro.criar(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(ExcecaoTransicaoStatusInvalida.class)
    public ResponseEntity<RespostaErro> tratarTransicaoStatusInvalida(ExcecaoTransicaoStatusInvalida ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespostaErro.criar(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespostaErro> tratarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespostaErro.criar(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }
}