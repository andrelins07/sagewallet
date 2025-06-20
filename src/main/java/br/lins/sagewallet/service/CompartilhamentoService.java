package br.lins.sagewallet.service;

import br.lins.sagewallet.controller.webscoket.NotificacaoWebsocketController;
import br.lins.sagewallet.dto.CompartilhamentoRequestDTO;
import br.lins.sagewallet.exception.ObjetoNaoEncontradoException;
import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.model.notificacao.Notificacao;
import br.lins.sagewallet.model.usuario.Usuario;
import br.lins.sagewallet.model.compartilhamento.EstadoSolicitacao;
import br.lins.sagewallet.repository.CompartilhamentoRepository;
import br.lins.sagewallet.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompartilhamentoService {

    private final UsuarioRepository usuarioRepository;

    private final NotificacaoWebsocketController notificacaoWebsocketController;

    private final CompartilhamentoRepository compartilhamentoRepository;

    public CompartilhamentoService(
            UsuarioRepository usuarioRepository,
            NotificacaoWebsocketController notificacaoWebsocketController,
            CompartilhamentoRepository compartilhamentoRepository)
    {
        this.usuarioRepository = usuarioRepository;
        this.notificacaoWebsocketController = notificacaoWebsocketController;
        this.compartilhamentoRepository = compartilhamentoRepository;
    }

    public List<Compartilhamento> listarTodos(){
        return compartilhamentoRepository.findAll();
    }

    public Compartilhamento listarPorId(Integer id){

        return compartilhamentoRepository.findById(id).
                orElseThrow(() -> new ObjetoNaoEncontradoException("Compartilhamento de ID " + id + " não foi localizado!"));
    }

    public Compartilhamento novaSolicitacao(CompartilhamentoRequestDTO compartilhamento) {

        Usuario remetente = usuarioRepository.findById(compartilhamento.idRemetente()).
                orElseThrow(() -> new ObjetoNaoEncontradoException("Usuario remetente não foi encontrado!"));

        Usuario destinatario = usuarioRepository.findById(compartilhamento.idDestinatario()).
                orElseThrow(() -> new ObjetoNaoEncontradoException("Usuario destinatario não foi encontrado!"));

        notificacaoWebsocketController.criarNotificacao(
                new Notificacao(
                        destinatario,
                        remetente.getNome() + " deseja compartilhar dados com você!"));

        return compartilhamentoRepository.save(new Compartilhamento(remetente, destinatario));
    }

    public Compartilhamento responderSolicitacao(Integer id, EstadoSolicitacao resposta) {

        Compartilhamento compartilhamento = compartilhamentoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Solicitação de compartilhamento com id " + id + "não encontrada!"));

        compartilhamento.responderSolicitacao(resposta);

        String mensagem = switch (resposta) {
            case APROVADO -> compartilhamento.getDestinatario().getNome() + " aceitou o compartilhamento!";
            case RECUSADO -> compartilhamento.getDestinatario().getNome() + " recusou o compartilhamento!";
            default -> mensagem = "Status invalido";
        };

        notificacaoWebsocketController.criarNotificacao(new Notificacao(compartilhamento.getRemetente(), mensagem));

        return compartilhamentoRepository.save(compartilhamento);
    }

    public void desfazerCompartilhamento(Integer id) {

        if (compartilhamentoRepository.findById(id).isEmpty()) {
            throw new ObjetoNaoEncontradoException("Compartilhamento de ID " + id + "não localizado!");
        }
        compartilhamentoRepository.deleteById(id);
    }
}
