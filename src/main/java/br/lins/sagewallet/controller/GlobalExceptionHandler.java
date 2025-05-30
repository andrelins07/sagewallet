package br.lins.sagewallet.controller;

import br.lins.sagewallet.exception.CategoriaNaoEncontradaException;
import br.lins.sagewallet.exception.LancamentoDuplicadoException;
import br.lins.sagewallet.exception.LancamentoNaoEncontradoException;
import br.lins.sagewallet.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(LancamentoDuplicadoException.class)
    public ResponseEntity<String> handleLancamentoDuplicado(LancamentoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(LancamentoNaoEncontradoException.class)
    public ResponseEntity<String> handleLancamentoNaoEncontrado(LancamentoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<String> handleCtegoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
