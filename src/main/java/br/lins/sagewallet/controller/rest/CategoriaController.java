package br.lins.sagewallet.controller.rest;

import br.lins.sagewallet.model.lancamento.Categoria;
import br.lins.sagewallet.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        return ResponseEntity.ok(categoriaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> listarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(categoriaService.listarPorId(id));
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Categoria>> listarPorDescricao(@PathVariable String descricao){
        return ResponseEntity.ok(categoriaService.listarPorDescricao(descricao));
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria){

        System.out.println("Nome: " + categoria.getNome() + "Descricao: " + categoria.getDescricao());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.cadastrar(categoria));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@Valid @RequestBody Categoria categoria, @PathVariable Integer id){
        return ResponseEntity.ok(categoriaService.atualizar(categoria, id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        categoriaService.deletar(id);
    }

}
