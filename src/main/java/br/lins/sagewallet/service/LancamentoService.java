package br.lins.sagewallet.service;

import br.lins.sagewallet.exception.DadosInconsistentesException;
import br.lins.sagewallet.exception.InformacoesDuplicadasException;
import br.lins.sagewallet.exception.ObjetoNaoEncontradoException;
import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.model.compartilhamento.EstadoSolicitacao;
import br.lins.sagewallet.model.lancamento.Lancamento;
import br.lins.sagewallet.model.usuario.Usuario;
import br.lins.sagewallet.repository.CategoriaRepository;
import br.lins.sagewallet.repository.CompartilhamentoRepository;
import br.lins.sagewallet.repository.LancamentoRepository;
import br.lins.sagewallet.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoService {

    private final UsuarioRepository usuarioRepository;

    private final LancamentoRepository lancamentoRepository;

    private final CategoriaRepository categoriaRepository;

    private final CompartilhamentoRepository compartilhamentoPerfilRepository;

    public LancamentoService(
            UsuarioRepository usuarioRepository,
            LancamentoRepository lancamentoRepository,
            CategoriaRepository categoriaRepository,
            CompartilhamentoRepository compartilhamentoPerfilRepository)
    {
        this.usuarioRepository = usuarioRepository;
        this.lancamentoRepository = lancamentoRepository;
        this.categoriaRepository = categoriaRepository;
        this.compartilhamentoPerfilRepository = compartilhamentoPerfilRepository;
    }

    public List<Lancamento> listar() {
        return lancamentoRepository.findAllWithCategoriaAndUsuario();
    }

    public Lancamento listarPorId(Long id){
        return lancamentoRepository.findById(id)
                .orElseThrow(()-> new ObjetoNaoEncontradoException("Lancamento de id " + id + " não encontrado!"));
    }

    public List<Lancamento> listarPorDescricao(String descricao){
        return lancamentoRepository
                .findAllByDescricaoContainingIgnoreCase(descricao);
    }

    public List<Lancamento> listarPorUsuario(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Usuario de id " + id + " não encontrado!"));

        return lancamentoRepository.findByUsuarioIn(buscarUsuariosCompartilhados(usuario));
    }

    public Lancamento criar(Lancamento lancamento) {

        validarExistenciaUsuario(lancamento.getUsuario().getId());

        validarExistenciaCategoria(lancamento.getCategoria().getId());

        if(lancamento.getId() != null){
            throw new DadosInconsistentesException("O ID não pode ser diferente de nulo no metodo POST");
        }

        validarDuplicidade(lancamento);

        return lancamentoRepository.save(lancamento);
    }

    public Lancamento atualizar(Lancamento lancamento, Long id) {

        if(!lancamento.getId().equals(id)) {
            throw new DadosInconsistentesException("ID do corpo e da URL não coincidem");
        }

        validarExistenciaLancamento(id);

        validarExistenciaCategoria(lancamento.getCategoria().getId());

        validarDuplicidade(lancamento);

        return lancamentoRepository.save(lancamento);
    }

    public void deletar(Long id){

        validarExistenciaLancamento(id);

        lancamentoRepository.deleteById(id);
    }

    private void validarDuplicidade(Lancamento lancamento) {

         lancamentoRepository
                .findByDescricaoAndValorAndDataAndUsuario(
                        lancamento.getDescricao(),
                        lancamento.getValor(),
                        lancamento.getData(),
                        lancamento.getUsuario()).ifPresent( l -> {
             if (!Objects.equals(lancamento.getId(), l.getId())){
                 throw new InformacoesDuplicadasException("Lancamento já cadastrado");
             }
         });
    }
    private void validarExistenciaUsuario(Integer id){
        if(!usuarioRepository.existsById(id))
            throw new ObjetoNaoEncontradoException("Usuario de id " + id + " não encontrado!");
    }
    private void validarExistenciaCategoria(Integer id){
        if (!categoriaRepository.existsById(id))
            throw new ObjetoNaoEncontradoException("Categoria não encontrada no sistema!");
    }
    private void validarExistenciaLancamento(Long id){
        if (!lancamentoRepository.existsById(id))
            throw new ObjetoNaoEncontradoException("Lancamento de id " + id + " não localizado no sistema!");
    }
    private List<Usuario> buscarUsuariosCompartilhados(Usuario usuario){

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(usuario);

        usuarios.addAll(compartilhamentoPerfilRepository
                .findByDestinatarioAndStatus(usuario, EstadoSolicitacao.APROVADO)
                .stream()
                .map(Compartilhamento::getRemetente).toList());

        return usuarios;
    }
}


