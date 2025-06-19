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
    public ResponseEntity<List<LancamentoResponseDTO>> listar(){

        return ResponseEntity.ok(lancamentoService.listar().stream().map(LancamentoResponseDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new LancamentoResponseDTO(lancamentoService.listarPorId(id)));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<LancamentoResponseDTO>> listarPorUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(lancamentoService.listarPorUsuario(id).stream().map(LancamentoResponseDTO::new).toList());
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<LancamentoResponseDTO>> listarPorDescricao(@PathVariable String descricao){
        return ResponseEntity.ok(lancamentoService.listarPorDescricao(descricao).stream().map(LancamentoResponseDTO::new).toList());
    }

    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> criar(@Valid @RequestBody LancamentoRequestDTO lancamento) {

        Lancamento novoLancamentoo = lancamentoService.criar(lancamento);

        webSocketIndicadoresController.carregarIndicadores(novoLancamentoo.getUsuario().getId());

        return ResponseEntity.ok(new LancamentoResponseDTO(novoLancamentoo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> atualizar(@Valid @RequestBody LancamentoRequestDTO lancamento, @PathVariable Long id) {

        Lancamento lancamentoAtualizado = lancamentoService.atualizar(lancamento, id);

        webSocketIndicadoresController.carregarIndicadores(lancamentoAtualizado.getUsuario().getId());

        return ResponseEntity.ok(new LancamentoResponseDTO(lancamentoAtualizado));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        lancamentoService.deletar(id);
    }
}
