package br.lins.sagewallet.controller.rest;

import br.lins.sagewallet.controller.webscoket.IndicadorWebSocketController;
import br.lins.sagewallet.dto.LancamentoRequestDTO;
import br.lins.sagewallet.dto.LancamentoResponseDTO;
import br.lins.sagewallet.model.lancamento.Lancamento;
import br.lins.sagewallet.service.LancamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<List<Lancamento>> listar(){
        return ResponseEntity.ok(lancamentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lancamentoService.listarPorId(id));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Lancamento>> listarPorUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(lancamentoService.listarPorUsuario(id));
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Lancamento>> listarPorDescricao(@PathVariable String descricao){
        return ResponseEntity.ok(lancamentoService.listarPorDescricao(descricao));
    }

    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> criar(@Valid @RequestBody LancamentoRequestDTO lancamento) {

        Lancamento novoLancamentoo = lancamentoService.criar(lancamento);

        webSocketIndicadoresController.carregarIndicadores(novoLancamentoo.getUsuario().getId());

        return ResponseEntity.ok(new LancamentoResponseDTO(novoLancamentoo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lancamento> atualizar(@Valid @RequestBody Lancamento lancamento, @PathVariable Long id) {

        Lancamento lancamentoAtualizado = lancamentoService.atualizar(lancamento, id);

        webSocketIndicadoresController.carregarIndicadores(lancamento.getUsuario().getId());

        return ResponseEntity.ok(lancamentoAtualizado);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        lancamentoService.deletar(id);
    }
}
