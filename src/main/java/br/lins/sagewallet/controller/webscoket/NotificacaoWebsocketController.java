package br.lins.sagewallet.controller.webscoket;

import br.lins.sagewallet.model.notificacao.Notificacao;
import br.lins.sagewallet.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificacaoWebsocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificacaoService notificacaoService;

    @MessageMapping("/exibir-notificacoes")
    @SendTo("/topics/notificacoes")
    public void criarNotificacao(Notificacao notificacao) {

        notificacaoService.criarNotificacao(notificacao);
        messagingTemplate.convertAndSend("/topics/notificacoes", notificacao);
    }
}
