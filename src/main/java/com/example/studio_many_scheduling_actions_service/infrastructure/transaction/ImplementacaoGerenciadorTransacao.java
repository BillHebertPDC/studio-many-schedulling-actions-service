package com.example.studio_many_scheduling_actions_service.infrastructure.transaction;

import com.example.studio_many_scheduling_actions_service.domain.port.GerenciadorTransacao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

@Component
public class ImplementacaoGerenciadorTransacao implements GerenciadorTransacao {

    private final TransactionTemplate transactionTemplate;

    public ImplementacaoGerenciadorTransacao(PlatformTransactionManager platformTransactionManager) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public <T> T executar(Supplier<T> acao) {
        return transactionTemplate.execute(status -> acao.get());
    }
}