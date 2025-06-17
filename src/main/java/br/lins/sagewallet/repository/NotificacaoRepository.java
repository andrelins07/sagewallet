package br.lins.sagewallet.repository;

import br.lins.sagewallet.model.notificacao.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
}
