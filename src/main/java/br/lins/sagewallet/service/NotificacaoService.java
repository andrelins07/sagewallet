package br.lins.sagewallet.service;

import br.lins.sagewallet.model.Notificacao;
import br.lins.sagewallet.model.Usuario;
import br.lins.sagewallet.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public void criarNotificacao(Notificacao notificacao) {
        notificacaoRepository.save(notificacao);
    }

}
