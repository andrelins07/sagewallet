package br.lins.sagewallet.repository;

import br.lins.sagewallet.model.Usuario;
import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.model.compartilhamento.EstadoSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompartilhamentoRepository extends JpaRepository<Compartilhamento, Integer> {

    @Query("SELECT c FROM Compartilhamento c WHERE c.destinatario = :usuario AND c.status = :status")
    List<Compartilhamento> findByDestinatarioAndStatus(
            @Param("usuario") Usuario usuarioCompartilhado,
            @Param("status") EstadoSolicitacao status
    );
}
