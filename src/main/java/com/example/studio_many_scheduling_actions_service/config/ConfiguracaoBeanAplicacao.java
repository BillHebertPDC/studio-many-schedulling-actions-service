package com.example.studio_many_scheduling_actions_service.config;

import com.example.studio_many_scheduling_actions_service.application.port.ServicoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.application.service.ServicoMudancaStatus;
import com.example.studio_many_scheduling_actions_service.application.service.ServicoNotificacaoStatus;
import com.example.studio_many_scheduling_actions_service.application.service.ServicoTransicaoAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.GerenciadorTransacao;
import com.example.studio_many_scheduling_actions_service.domain.port.NotificadorWhatsApp;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.ValidadorTransicao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ponto de composicao da camada de aplicacao. Os servicos sao classes puras
 * (sem anotacoes Spring) e sao registrados aqui.
 */
@Configuration
public class ConfiguracaoBeanAplicacao {

    @Bean
    public ServicoTransicaoAgendamento servicoTransicaoAgendamento(RepositorioAgendamento repositorio,
                                                                   ValidadorTransicao validadorTransicao,
                                                                   GerenciadorTransacao gerenciadorTransacao) {
        return new ServicoTransicaoAgendamento(repositorio, validadorTransicao, gerenciadorTransacao);
    }

    @Bean
    public ServicoNotificacaoStatus servicoNotificacaoStatus(NotificadorWhatsApp notificadorWhatsApp) {
        return new ServicoNotificacaoStatus(notificadorWhatsApp);
    }

    @Bean
    public ServicoAcaoAgendamento servicoAcaoAgendamento(ServicoTransicaoAgendamento servicoTransicao,
                                                         ServicoNotificacaoStatus servicoNotificacao) {
        return new ServicoMudancaStatus(servicoTransicao, servicoNotificacao);
    }
}