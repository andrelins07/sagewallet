package br.lins.sagewallet.controller;

import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import br.lins.sagewallet.model.compartilhamento.EstadoSolicitacao;
import br.lins.sagewallet.service.CompartilhamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/compartilhamento")
public class CompartilhamentoController {

    private final CompartilhamentoService compartilhamentoService;

    public CompartilhamentoController(CompartilhamentoService compartilhamentoService) {
        this.compartilhamentoService = compartilhamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Compartilhamento>> listar(){
        return ResponseEntity.ok(compartilhamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compartilhamento> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(compartilhamentoService.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Compartilhamento> criar(@RequestBody @Valid Compartilhamento compartilhamento){
        return ResponseEntity.status(HttpStatus.CREATED).
                body(compartilhamentoService.novaSolicitacao(compartilhamento));
    }

    @PutMapping("/{id}/resposta")
    public ResponseEntity<Compartilhamento> responderSolicitacao(
            @PathVariable Integer id,
            @RequestParam EstadoSolicitacao status) {
        return ResponseEntity.ok(compartilhamentoService.responderSolicitacao(id, status));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        compartilhamentoService.desfazerCompartilhamento(id);
    }
}

