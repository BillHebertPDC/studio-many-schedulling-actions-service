package com.example.studio_many_scheduling_actions_service.config;

import com.example.studio_many_scheduling_actions_service.domain.port.ValidadorTransicao;
import com.example.studio_many_scheduling_actions_service.domain.service.ConfiguracaoFluxoAgendamento;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoBeanDominio {

    @Bean
    public ValidadorTransicao validadorTransicao() {
        return new ConfiguracaoFluxoAgendamento();
    }
}