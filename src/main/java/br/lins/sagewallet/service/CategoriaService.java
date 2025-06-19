package br.lins.sagewallet.service;

import br.lins.sagewallet.exception.DadosInconsistentesException;
import br.lins.sagewallet.exception.InformacoesDuplicadasException;
import br.lins.sagewallet.exception.ObjetoNaoEncontradoException;
import br.lins.sagewallet.model.lancamento.Categoria;
import br.lins.sagewallet.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarPorDescricao(String descricao) {
        return categoriaRepository.findAllByDescricaoContainingIgnoreCase(descricao);
    }

    public Categoria listarPorId(Integer id) {

        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Categoria não encontrada no sistema!"));
    }
    public Categoria cadastrar(Categoria categoria) {

        if(categoriaRepository.findByNomeIgnoreCase(categoria.getNome()).isPresent()){
            throw new InformacoesDuplicadasException("Já existente uma categoria com esse nome");
        }

        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Categoria categoria, Integer id){

        if(!categoria.getId().equals(id))
            throw new DadosInconsistentesException("ID do corpo e da URL não coincidem");

        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Categoria não encontrada no sistema!"));

        categoriaRepository.findByNomeIgnoreCase(categoria.getNome())
                .filter(c -> !c.getId().equals(id))
                .ifPresent(c -> {
                    throw new InformacoesDuplicadasException("Já existe uma categoria com esse nome");
                });

        categoriaExistente.atualizar(categoria);

        return categoriaRepository.save(categoriaExistente);
    }

    public void deletar(Integer id){

        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if(categoria.isEmpty())
            throw new ObjetoNaoEncontradoException("Categoria não encontrada no sistema!");

        categoriaRepository.deleteById(id);
    }
}
