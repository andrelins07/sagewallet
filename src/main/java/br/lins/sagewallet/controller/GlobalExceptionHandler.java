package br.lins.sagewallet.controller;

import br.lins.sagewallet.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 404));
    }

    @ExceptionHandler(LancamentoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleLancamentoDuplicado(LancamentoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 400));
    }

    @ExceptionHandler(LancamentoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleLancamentoNaoEncontrado(LancamentoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 404));
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 404));
    }


    @ExceptionHandler(EmailJaExistenteException.class)
    public ResponseEntity<ErrorResponse> handleNomeDeEmailJaCadastrado(EmailJaExistenteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 400));
    }

    @ExceptionHandler(NomeDeUsuarioJaExistenteException.class)
    public ResponseEntity<ErrorResponse> handleNomeDeUsuarioJaCadastrado(NomeDeUsuarioJaExistenteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 400));
    }

    @ExceptionHandler(DadosInconsistentesException.class)
    public ResponseEntity<ErrorResponse> handleDadosInconsistentes(DadosInconsistentesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 400));
    }

}
