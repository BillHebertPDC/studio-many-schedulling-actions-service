package com.example.studio_many_scheduling_actions_service.infrastructure.repository;

import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioTemplateMensagem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdaptadorRepositorioTemplateMensagem implements RepositorioTemplateMensagem {

    private final JdbcTemplate jdbcTemplate;

    public AdaptadorRepositorioTemplateMensagem(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<String> buscarTemplate(StatusAgendamento status) {
        return jdbcTemplate.query(
                "SELECT template FROM template_mensagem WHERE status = ?",
                (rs, rowNum) -> rs.getString("template"),
                status.name()
        ).stream().findFirst();
    }
}