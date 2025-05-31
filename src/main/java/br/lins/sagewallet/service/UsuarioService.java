package br.lins.sagewallet.service;

import java.util.List;
import java.util.Optional;
import br.lins.sagewallet.exception.DadosInconsistentesException;
import br.lins.sagewallet.exception.EmailJaExistenteException;
import br.lins.sagewallet.exception.NomeDeUsuarioJaExistenteException;
import br.lins.sagewallet.exception.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;
import br.lins.sagewallet.model.Usuario;
import br.lins.sagewallet.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Integer id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(usuario.isEmpty()){
            throw new UsuarioNaoEncontradoException(id);
        }

        return usuario.get();
    }

    public Usuario cadastrarUsuario(Usuario usuario) {

        validarUsuarioUnico(usuario);

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario, Integer id) {

        if(!id.equals(usuario.getId()))
            throw new DadosInconsistentesException("ID do corpo e da URL não coincidem");

        if (usuarioRepository.findById(id).isEmpty())
            throw new UsuarioNaoEncontradoException(id);

        validarUsuarioUnico(usuario);

        return usuarioRepository.save(usuario);

    }

    public void deletarUsuario(Integer id){

        if (usuarioRepository.findById(id).isEmpty()) {
            throw new UsuarioNaoEncontradoException(id);
        }
        usuarioRepository.deleteById(id);
    }
    private void validarUsuarioUnico(Usuario usuario) {
        Optional<Usuario> existentePorEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (existentePorEmail.isPresent() && !existentePorEmail.get().getId().equals(usuario.getId())) {
            throw new EmailJaExistenteException();
        }

        Optional<Usuario> existentePorNomeUsuario = usuarioRepository.findByNomeUsuario(usuario.getNomeUsuario());
        if (existentePorNomeUsuario.isPresent() && !existentePorNomeUsuario.get().getId().equals(usuario.getId())) {
            throw new NomeDeUsuarioJaExistenteException();
        }
    }
}
