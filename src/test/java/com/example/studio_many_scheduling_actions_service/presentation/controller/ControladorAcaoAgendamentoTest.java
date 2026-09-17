package com.example.studio_many_scheduling_actions_service.presentation.controller;

import com.example.studio_many_scheduling_actions_service.application.dto.RequisicaoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.application.dto.RespostaAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.application.port.ServicoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoAgendamentoNaoEncontrado;
import com.example.studio_many_scheduling_actions_service.domain.exception.ExcecaoTransicaoStatusInvalida;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ControladorAcaoAgendamento.class)
class ControladorAcaoAgendamentoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicoAcaoAgendamento servicoAcaoAgendamento;

    @Test
    void deveRetornar200QuandoAcaoExecutada() throws Exception {
        when(servicoAcaoAgendamento.executarAcao(eq(1L), any(RequisicaoAcaoAgendamento.class)))
                .thenReturn(new RespostaAcaoAgendamento(
                        1L,
                        StatusAgendamento.AGUARDANDO_SINAL,
                        StatusAgendamento.CONFIRMADO_CLIENTE,
                        "Agendamento atualizado para Confirmado Cliente"
                ));

        mockMvc.perform(patch("/api/v1/scheduling/1/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"acao\":\"CONFIRMADO_CLIENTE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAgendamento").value(1))
                .andExpect(jsonPath("$.statusAnterior").value("AGUARDANDO_SINAL"))
                .andExpect(jsonPath("$.novoStatus").value("CONFIRMADO_CLIENTE"));
    }

    @Test
    void deveRetornar404QuandoAgendamentoNaoEncontrado() throws Exception {
        when(servicoAcaoAgendamento.executarAcao(eq(1L), any(RequisicaoAcaoAgendamento.class)))
                .thenThrow(new ExcecaoAgendamentoNaoEncontrado(1L));

        mockMvc.perform(patch("/api/v1/scheduling/1/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"acao\":\"CONFIRMADO_CLIENTE\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoTransicaoInvalida() throws Exception {
        when(servicoAcaoAgendamento.executarAcao(eq(1L), any(RequisicaoAcaoAgendamento.class)))
                .thenThrow(new ExcecaoTransicaoStatusInvalida(
                        StatusAgendamento.AGUARDANDO_SINAL,
                        StatusAgendamento.CHECKIN
                ));

        mockMvc.perform(patch("/api/v1/scheduling/1/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"acao\":\"CHECKIN\"}"))
                .andExpect(status().isBadRequest());
    }
}