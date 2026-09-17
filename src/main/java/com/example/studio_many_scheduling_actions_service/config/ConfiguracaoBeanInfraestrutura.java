package com.example.studio_many_scheduling_actions_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Ponto de composicao de infraestrutura. Objetos de adaptadores que nao sao
 * registrados por anotacao.
 */
@Configuration
public class ConfiguracaoBeanInfraestrutura {

    @Bean
    @Scope("prototype")
    public RestClient.Builder construtorRestClient() {
        SimpleClientHttpRequestFactory fabricaRequisicao = new SimpleClientHttpRequestFactory();
        fabricaRequisicao.setConnectTimeout(10_000);
        fabricaRequisicao.setReadTimeout(30_000);
        return RestClient.builder().requestFactory(fabricaRequisicao);
    }
}