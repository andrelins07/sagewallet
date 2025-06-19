package br.lins.sagewallet.repository;

import br.lins.sagewallet.model.usuario.Usuario;
import br.lins.sagewallet.model.lancamento.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query("""
        SELECT l FROM Lancamento l 
        JOIN FETCH l.categoria 
        JOIN FETCH l.usuario 
        WHERE l.descricao LIKE CONCAT('%', :descricaoParte, '%')
    """)
    List<Lancamento> findAllByDescricaoContainingIgnoreCase(@Param("descricaoParte") String descricao);

    @Query("""
        SELECT l FROM Lancamento l 
        JOIN FETCH l.categoria 
        JOIN FETCH l.usuario 
        WHERE l.usuario IN :usuarios
    """)
    List<Lancamento> findByUsuarioIn(@Param("usuarios") List<Usuario> usuarios);

    @Query("""
        SELECT l FROM Lancamento l 
        JOIN FETCH l.categoria 
        JOIN FETCH l.usuario
    """)
    List<Lancamento> findAllWithCategoriaAndUsuario();

    List<Lancamento> findByUsuarioId(Integer id);

    Optional<Lancamento> findByDescricaoAndValorAndDataAndUsuario(String descricao, BigDecimal valor, LocalDate data, Usuario usuario);


}
