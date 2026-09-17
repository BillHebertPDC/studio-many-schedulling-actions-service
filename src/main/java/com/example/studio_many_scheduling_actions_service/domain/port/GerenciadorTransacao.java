package com.example.studio_many_scheduling_actions_service.domain.port;

import java.util.function.Supplier;

public interface GerenciadorTransacao {

    <T> T executar(Supplier<T> acao);
}