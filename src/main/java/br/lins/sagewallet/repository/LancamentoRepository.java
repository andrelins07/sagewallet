package br.lins.sagewallet.repository;

import br.lins.sagewallet.model.usuario.Usuario;
import br.lins.sagewallet.model.lancamento.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findAllByDescricaoContainingIgnoreCase(String descricao);

    List<Lancamento> findByUsuarioId(Integer id);

    @Query("SELECT l FROM Lancamento l JOIN FETCH l.categoria WHERE l.usuario IN :usuarios")
    List<Lancamento> findByUsuarioIn(List<Usuario> usuarios);

    Optional<Lancamento> findByDescricaoAndValorAndDataAndUsuario(String descricao, BigDecimal valor, LocalDate data, Usuario usuario);

    @Query("SELECT l FROM Lancamento l JOIN FETCH l.categoria JOIN FETCH l.usuario")
    List<Lancamento> findAllWithCategoriaAndUsuario();


}
