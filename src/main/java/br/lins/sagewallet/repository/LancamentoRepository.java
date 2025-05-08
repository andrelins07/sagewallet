package br.lins.sagewallet.repository;

import br.lins.sagewallet.model.lancamento.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findAllByDescricaoContainingIgnoreCase(String descricao);

}
