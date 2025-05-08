package br.lins.sagewallet.controller;

import br.lins.sagewallet.model.lancamento.Lancamento;
import br.lins.sagewallet.repository.CategoriaRepository;
import br.lins.sagewallet.repository.LancamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/lancamentos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LancamentoController {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Lancamento>> getAll(){
        return ResponseEntity.ok(lancamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> findLancamentoById(@PathVariable Long id) {

        return lancamentoRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Lancamento>> getCategoriaByNome(@PathVariable String descricao){
        return ResponseEntity.ok(lancamentoRepository
                .findAllByDescricaoContainingIgnoreCase(descricao));
    }

    @PostMapping
    public ResponseEntity<Lancamento> postPostagem(@Valid @RequestBody Lancamento lancamento) {

        if (categoriaRepository.existsById(lancamento.getCategoria().getId()))
            return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoRepository.save(lancamento));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
    }

    @PutMapping
    public ResponseEntity<Lancamento> putPostagem(@Valid @RequestBody Lancamento lancamento) {

        if (lancamentoRepository.existsById(lancamento.getId())) {

            if (categoriaRepository.existsById(lancamento.getCategoria().getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(lancamentoRepository.save(lancamento));
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePostagem(@PathVariable Long id) {

        Optional<Lancamento> postagem = lancamentoRepository.findById(id);

        if (postagem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        lancamentoRepository.deleteById(id);
    }
}
