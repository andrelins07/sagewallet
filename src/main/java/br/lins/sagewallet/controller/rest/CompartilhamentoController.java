package br.lins.sagewallet.controller.rest;

import br.lins.sagewallet.dto.CompartilhamentoRequestDTO;
import br.lins.sagewallet.dto.CompartilhamentoResponseDTO;
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
    public ResponseEntity<List<CompartilhamentoResponseDTO>> listar(){
        return ResponseEntity.ok(compartilhamentoService.listarTodos().stream().map(CompartilhamentoResponseDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompartilhamentoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(new CompartilhamentoResponseDTO(compartilhamentoService.listarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<CompartilhamentoResponseDTO> criar(@RequestBody @Valid CompartilhamentoRequestDTO compartilhamento){
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new CompartilhamentoResponseDTO(compartilhamentoService.novaSolicitacao(compartilhamento)));
    }

    @PutMapping("/{id}/resposta")
    public ResponseEntity<CompartilhamentoResponseDTO> responderSolicitacao(
            @PathVariable Integer id,
            @RequestParam EstadoSolicitacao status) {
        return ResponseEntity.ok(new CompartilhamentoResponseDTO(compartilhamentoService.responderSolicitacao(id, status)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        compartilhamentoService.desfazerCompartilhamento(id);
    }
}

