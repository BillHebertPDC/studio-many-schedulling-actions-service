package com.example.studio_many_scheduling_actions_service.infrastructure.whatsapp;

import com.example.studio_many_scheduling_actions_service.domain.model.MensagemAgendamento;
import com.example.studio_many_scheduling_actions_service.domain.port.RepositorioTemplateMensagem;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * Formata o texto da mensagem de WhatsApp de acordo com o status. O texto do
 * template vem do banco (editavel via H2) e o formato (marcacao do WhatsApp,
 * data) e especifico do canal, por isso vive na infraestrutura.
 */
@Component
public class FormatadorMensagemWaha {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final String TEMPLATE_PADRAO = "Olá! O status do seu agendamento mudou para: *%s*.";

    private final RepositorioTemplateMensagem repositorioTemplate;

    public FormatadorMensagemWaha(RepositorioTemplateMensagem repositorioTemplate) {
        this.repositorioTemplate = repositorioTemplate;
    }

    public String formatar(MensagemAgendamento mensagem) {
        String template = repositorioTemplate.buscarTemplate(mensagem.getAcao())
                .orElse(TEMPLATE_PADRAO);
        String data = formatarData(mensagem);
        return String.format(template, data);
    }

    private String formatarData(MensagemAgendamento mensagem) {
        if (mensagem.getDataAgendada() == null) {
            return "o horário previamente informado";
        }
        return mensagem.getDataAgendada().format(FORMATO_DATA);
    }
}