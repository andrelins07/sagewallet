package br.lins.sagewallet.repository;

import br.lins.sagewallet.model.lancamento.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
