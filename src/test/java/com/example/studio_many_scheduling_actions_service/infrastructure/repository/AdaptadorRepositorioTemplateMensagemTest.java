package com.example.studio_many_scheduling_actions_service.infrastructure.repository;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest(properties = "spring.sql.init.mode=always")
@Import(AdaptadorRepositorioTemplateMensagem.class)
class AdaptadorRepositorioTemplateMensagemTest {

    @Autowired
    private AdaptadorRepositorioTemplateMensagem adaptador;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void deveBuscarTemplatePorStatus() {
        Optional<String> template = adaptador.buscarTemplate(StatusAgendamento.CHECKIN);

        assertTrue(template.isPresent());
        assertTrue(template.get().contains("check-in"));
    }

    @Test
    void deveRetornarVazioQuandoNaoExiste() {
        jdbcTemplate.update("DELETE FROM template_mensagem WHERE status = 'NO_SHOW'");

        assertTrue(adaptador.buscarTemplate(StatusAgendamento.NO_SHOW).isEmpty());
    }
}