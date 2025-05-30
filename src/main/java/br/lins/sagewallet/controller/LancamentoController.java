package br.lins.sagewallet.controller;

import br.lins.sagewallet.model.lancamento.Lancamento;
import br.lins.sagewallet.service.LancamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LancamentoController {

    private final IndicadorWebSocketController webSocketIndicadoresController;
    private final LancamentoService lancamentoService;

    public LancamentoController(
            IndicadorWebSocketController webSocketIndicadoresController,
            LancamentoService lancamentoService
    ) {
        this.webSocketIndicadoresController = webSocketIndicadoresController;
        this.lancamentoService = lancamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Lancamento>> getAll(){
        return ResponseEntity.ok(lancamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> findLancamentoById(@PathVariable Long id) {

        return lancamentoService.listarPorId(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Lancamento>> findLancamentoByUser(@PathVariable Integer id) {

        return ResponseEntity.ok(lancamentoService.listarPorUsuario(id));
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Lancamento>> buscarLancamentoPorDescricao(@PathVariable String descricao){
        return ResponseEntity.ok(lancamentoService.listarPorDescricao(descricao));
    }

    @PostMapping
    public ResponseEntity<Lancamento> cadastrarLancamento(@Valid @RequestBody Lancamento lancamento) {

        Lancamento novo = lancamentoService.cadastrarLancamento(lancamento);

        webSocketIndicadoresController.carregarIndicadores(lancamento.getUsuario().getId());

        return ResponseEntity.ok(novo);
    }

    @PutMapping
    public ResponseEntity<Lancamento> atualizarLancamento(@Valid @RequestBody Lancamento lancamento) {

        Optional<Lancamento> lancamentoAtualizado = lancamentoService.atualizarLancamento(lancamento);

        return lancamentoAtualizado
                .map(l -> {
                    webSocketIndicadoresController.carregarIndicadores(lancamento.getUsuario().getId());
                    return ResponseEntity.ok(l);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletarLancamento(@PathVariable Long id) {

        lancamentoService.deletarLancamento(id);

    }
}
