package br.lins.sagewallet.controller;

import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.repository.CompartilhamentoRepository;
import br.lins.sagewallet.service.CompartilhamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compartilhamento")
public class CompartilhamentoController {

    @Autowired
    private CompartilhamentoRepository compartilhamentoRepository;

    @Autowired
    private CompartilhamentoService compartilhamentoService;

    @GetMapping
    public ResponseEntity<List<Compartilhamento>> getAllCompartilhamentos(){
        return ResponseEntity.ok(compartilhamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compartilhamento> buscarCompartilhamentoPorId(@PathVariable Integer id) {

        return compartilhamentoRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping
    public ResponseEntity<Compartilhamento> novoCompartilhamento(@RequestBody @Valid Compartilhamento compartilhamento){

        return ResponseEntity.status(HttpStatus.CREATED).body(compartilhamentoService.novaSolicitacao(compartilhamento));
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<Compartilhamento> atualizarCompartilhamento(@Valid @RequestBody Compartilhamento compartilhamento) {

        return ResponseEntity.ok(compartilhamentoService.responderSolicitacao(compartilhamento));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletarCompartilhamento(@PathVariable Integer id) {

        Optional<Compartilhamento> compartilhamentoPerfis = compartilhamentoRepository.findById(id);

        if (compartilhamentoPerfis.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        compartilhamentoRepository.deleteById(id);
    }
}

