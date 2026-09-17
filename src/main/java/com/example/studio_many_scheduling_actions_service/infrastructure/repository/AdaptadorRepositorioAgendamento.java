package com.example.studio_many_scheduling_actions_service.infrastructure.repository;

import com.example.studio_many_scheduling_actions_service.domain.model.Agendamento;
import com.example.studio_many_scheduling_actions_service.domain.model.StatusAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioAgendamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

@Component
public class AdaptadorRepositorioAgendamento implements RepositorioAgendamento {

    private static final String COLUNAS = "id, id_cliente, id_profissional, data_agendada, status, telefone_cliente";

    private static final RowMapper<Agendamento> CONVERSOR_LINHA = (rs, rowNum) -> new Agendamento(
            rs.getLong("id"),
            rs.getLong("id_cliente"),
            rs.getLong("id_profissional"),
            rs.getTimestamp("data_agendada").toLocalDateTime(),
            StatusAgendamento.valueOf(rs.getString("status")),
            rs.getString("telefone_cliente")
    );

    private final JdbcTemplate jdbcTemplate;

    public AdaptadorRepositorioAgendamento(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Agendamento> buscarPorId(Long id) {
        return jdbcTemplate.query("SELECT " + COLUNAS + " FROM agendamento WHERE id = ?", CONVERSOR_LINHA, id)
                .stream()
                .findFirst();
    }

    @Override
    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getId() == null) {
            return inserir(agendamento);
        }
        return atualizar(agendamento);
    }

    private Agendamento inserir(Agendamento agendamento) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO agendamento (id_cliente, id_profissional, data_agendada, status, telefone_cliente, criado_em, atualizado_em) "
                            + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, agendamento.getIdCliente());
            ps.setLong(2, agendamento.getIdProfissional());
            ps.setTimestamp(3, Timestamp.valueOf(agendamento.getDataAgendada()));
            ps.setString(4, agendamento.getStatus().name());
            ps.setString(5, agendamento.getTelefoneCliente());
            return ps;
        }, keyHolder);

        return new Agendamento(
                keyHolder.getKey().longValue(),
                agendamento.getIdCliente(),
                agendamento.getIdProfissional(),
                agendamento.getDataAgendada(),
                agendamento.getStatus(),
                agendamento.getTelefoneCliente()
        );
    }

    private Agendamento atualizar(Agendamento agendamento) {
        jdbcTemplate.update(
                "UPDATE agendamento SET data_agendada = ?, status = ?, telefone_cliente = ?, atualizado_em = CURRENT_TIMESTAMP WHERE id = ?",
                Timestamp.valueOf(agendamento.getDataAgendada()),
                agendamento.getStatus().name(),
                agendamento.getTelefoneCliente(),
                agendamento.getId()
        );
        return agendamento;
    }
}