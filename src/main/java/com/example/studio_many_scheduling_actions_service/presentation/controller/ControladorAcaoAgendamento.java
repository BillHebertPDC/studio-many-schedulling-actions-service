package com.example.studio_many_scheduling_actions_service.presentation.controller;
// aplication imports
// DTO de resposta do application para o presentation obviamente
import com.example.studio_many_scheduling_actions_service.application.dto.RespostaAcaoAgendamento;
// Port/caminho para o application
import com.example.studio_many_scheduling_actions_service.application.port.ServicoAcaoAgendamento;
// presentation imports
//DTOs para entrada e saida do presentation
import com.example.studio_many_scheduling_actions_service.presentation.dto.ApiRequisicaoAcaoAgendamento;
import com.example.studio_many_scheduling_actions_service.presentation.dto.ApiRespostaAcaoAgendamento;
// Spring 
// resposta
import org.springframework.http.ResponseEntity;
// Rota ( Base )
import org.springframework.web.bind.annotation.PatchMapping;
// Variavel na url
import org.springframework.web.bind.annotation.PathVariable;
// Corpo hhpt
import org.springframework.web.bind.annotation.RequestBody;
// Rota
import org.springframework.web.bind.annotation.RequestMapping;
// Spring Bean notation
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scheduling")
public class ControladorAcaoAgendamento {

    // esse será o caminho/porta para a application ( acesso a application )
    private final ServicoAcaoAgendamento servicoAcaoAgendamento;

    // Injeção de dependencia de service
    public ControladorAcaoAgendamento(ServicoAcaoAgendamento servicoAcaoAgendamento) {
        this.servicoAcaoAgendamento = servicoAcaoAgendamento;
    }

    @PatchMapping("/{id}/action")
    public ResponseEntity<ApiRespostaAcaoAgendamento> executarAcao(@PathVariable Long id,
            @RequestBody ApiRequisicaoAcaoAgendamento requisicao) {
        if (requisicao.acao() == null) {
            throw new IllegalArgumentException("Acao e obrigatoria");
        }

        RespostaAcaoAgendamento resposta = servicoAcaoAgendamento.executarAcao(id,
                requisicao.converterParaRequisicaoAplicacao());
        return ResponseEntity.ok(ApiRespostaAcaoAgendamento.criarDeRespostaAplicacao(resposta));
    }
}