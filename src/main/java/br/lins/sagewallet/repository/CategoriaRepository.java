package br.lins.sagewallet.repository;

import br.lins.sagewallet.model.lancamento.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findAllByDescricaoContainingIgnoreCase(String descricao);

    Optional<Categoria> findByNomeIgnoreCase(String nome);
}
