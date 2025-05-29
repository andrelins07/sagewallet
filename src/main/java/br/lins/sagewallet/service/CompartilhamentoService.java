package br.lins.sagewallet.service;

import br.lins.sagewallet.controller.NotificacaoWebsocketController;
import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.model.Notificacao;
import br.lins.sagewallet.model.Usuario;
import br.lins.sagewallet.repository.CompartilhamentoRepository;
import br.lins.sagewallet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CompartilhamentoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacaoWebsocketController notificacaoWebsocketController;

    @Autowired
    private CompartilhamentoRepository compartilhamentoRepository;

    public Compartilhamento novaSolicitacao(Compartilhamento compartilhamento) {

        Optional<Usuario> usuario = usuarioRepository.findById(compartilhamento.getDestinatario().getId());

        if(usuario.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuario que deseja compartilhar não foi encontrado!");

        notificacaoWebsocketController.criarNotificacao(
                new Notificacao(
                        compartilhamento.getDestinatario(),
                        compartilhamento.getRemetente().getNome() + " deseja compartilhar dados com você!"));

        return compartilhamentoRepository.save(compartilhamento);

    }

    public Compartilhamento responderSolicitacao(Compartilhamento compartilhamento) {

        if (!compartilhamentoRepository.existsById(compartilhamento.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação de compartilhamento não encontrada!");

        String mensagem;

        switch (compartilhamento.getStatus()) {
            case APROVADO -> {
                mensagem = compartilhamento.getDestinatario().getNome() + " aceitou o compartilhamento!";
                compartilhamento.setDataCompartilhamento(LocalDate.now());
            }
            case RECUSADO ->
                    mensagem = compartilhamento.getDestinatario().getNome() + " recusou o compartilhamento!";
            default -> mensagem = "Status invalido";

        }

        notificacaoWebsocketController.criarNotificacao(new Notificacao(compartilhamento.getRemetente(), mensagem));

        return compartilhamentoRepository.save(compartilhamento);
    }
}
