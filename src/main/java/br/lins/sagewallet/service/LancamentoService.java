package br.lins.sagewallet.service;

import br.lins.sagewallet.dto.LancamentoRequestDTO;
import br.lins.sagewallet.exception.DadosInconsistentesException;
import br.lins.sagewallet.exception.InformacoesDuplicadasException;
import br.lins.sagewallet.exception.ObjetoNaoEncontradoException;
import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.model.compartilhamento.EstadoSolicitacao;
import br.lins.sagewallet.model.lancamento.Categoria;
import br.lins.sagewallet.model.lancamento.Lancamento;
import br.lins.sagewallet.model.usuario.Usuario;
import br.lins.sagewallet.repository.CategoriaRepository;
import br.lins.sagewallet.repository.CompartilhamentoRepository;
import br.lins.sagewallet.repository.LancamentoRepository;
import br.lins.sagewallet.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public Lancamento criar(LancamentoRequestDTO lancamento) {

        Usuario usuario = validarExistenciaUsuario(lancamento.idUsuario());

        Categoria categoria = validarExistenciaCategoria(lancamento.idCategoria());

        validarDuplicidade(null, lancamento.descricao(), lancamento.valor(), lancamento.data(), usuario);

        return lancamentoRepository.save(new Lancamento(lancamento, usuario, categoria));
    }

    public Lancamento atualizar(Lancamento lancamento, Long id) {

        if(!lancamento.getId().equals(id)) {
            throw new DadosInconsistentesException("ID do corpo e da URL não coincidem");
        }

        validarExistenciaLancamento(id);

        validarExistenciaCategoria(lancamento.getCategoria().getId());

        //validarDuplicidade(lancamento);

        return lancamentoRepository.save(lancamento);
    }

    public void deletar(Long id){

        validarExistenciaLancamento(id);

        lancamentoRepository.deleteById(id);
    }

    private void validarDuplicidade(Long idLancamento, String descricao, BigDecimal valor, LocalDate data, Usuario usuario) {

         lancamentoRepository
                 .findByDescricaoAndValorAndDataAndUsuario(descricao, valor, data, usuario)
                 .ifPresent( l -> {
                     if (!Objects.equals(idLancamento, l.getId())){
                         throw new InformacoesDuplicadasException("Lancamento já cadastrado");
                     }
                 });
    }
    private Usuario validarExistenciaUsuario(Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Usuario de id " + id + " não encontrado!"));
    }
    private Categoria validarExistenciaCategoria(Integer id){
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Categoria não encontrada no sistema!"));
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


