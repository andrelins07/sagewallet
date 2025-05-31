package br.lins.sagewallet.service;

import br.lins.sagewallet.exception.CategoriaNaoEncontradaException;
import br.lins.sagewallet.exception.LancamentoDuplicadoException;
import br.lins.sagewallet.exception.LancamentoNaoEncontradoException;
import br.lins.sagewallet.exception.UsuarioNaoEncontradoException;
import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.model.compartilhamento.EstadoSolicitacao;
import br.lins.sagewallet.model.lancamento.Lancamento;
import br.lins.sagewallet.model.Usuario;
import br.lins.sagewallet.repository.CategoriaRepository;
import br.lins.sagewallet.repository.CompartilhamentoRepository;
import br.lins.sagewallet.repository.LancamentoRepository;
import br.lins.sagewallet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CompartilhamentoRepository compartilhamentoPerfilRepository;

    public List<Lancamento> listarTodos() {
        return lancamentoRepository.findAllWithCategoriaAndUsuario();
    }
    public Optional<Lancamento> listarPorId(Long id){
        return lancamentoRepository.findById(id);
    }

    public List<Lancamento> listarPorDescricao(String descricao){
        return lancamentoRepository
                .findAllByDescricaoContainingIgnoreCase(descricao);
    }

    public List<Lancamento> listarPorUsuario(Integer id) {

        return usuarioRepository.findById(id).map(usuario -> {
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(usuario);
            usuarios.addAll(compartilhamentoPerfilRepository
                    .findByDestinatarioAndStatus(usuario, EstadoSolicitacao.APROVADO)
                    .stream()
                    .map(Compartilhamento::getRemetente).toList());
            return lancamentoRepository.findByUsuarioIn(usuarios);
        }).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public Lancamento cadastrarLancamento(Lancamento lancamento) {

        if (lancamentoRepository.existsByDescricaoAndValorAndDataAndUsuario(
                lancamento.getDescricao(), lancamento.getValor(), lancamento.getData(), lancamento.getUsuario())) {
            throw new LancamentoDuplicadoException();
        }

        if(usuarioRepository.findById(lancamento.getUsuario().getId()).isEmpty()){
            throw new UsuarioNaoEncontradoException(lancamento.getUsuario().getId());
        }

        if (!categoriaRepository.existsById(lancamento.getCategoria().getId()))
            throw new CategoriaNaoEncontradaException();

        return lancamentoRepository.save(lancamento);
    }

    public Optional<Lancamento> atualizarLancamento(Lancamento lancamento) {

        if (!lancamentoRepository.existsById(lancamento.getId()))
            return Optional.empty();

        if (lancamentoRepository.existsByDescricaoAndValorAndDataAndUsuario(
                lancamento.getDescricao(), lancamento.getValor(), lancamento.getData(), lancamento.getUsuario()))
            throw new LancamentoDuplicadoException();

        if (!categoriaRepository.existsById(lancamento.getCategoria().getId()))
            throw new CategoriaNaoEncontradaException();

        return Optional.of(lancamentoRepository.save(lancamento));
    }

    public void deletarLancamento(Long id){

        if (!lancamentoRepository.existsById(id))
            throw new LancamentoNaoEncontradoException();

        lancamentoRepository.deleteById(id);
    }
}


