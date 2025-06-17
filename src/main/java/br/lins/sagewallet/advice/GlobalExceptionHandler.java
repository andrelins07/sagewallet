package br.lins.sagewallet.advice;

import br.lins.sagewallet.dto.ErrorResponse;
import br.lins.sagewallet.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InformacoesDuplicadasException.class)
    public ResponseEntity<ErrorResponse> handleInformacoesDuplicadas(InformacoesDuplicadasException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 400));
    }

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleObjetoNaoEncontrado(ObjetoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 404));
    }

    @ExceptionHandler(DadosInconsistentesException.class)
    public ResponseEntity<ErrorResponse> handleDadosInconsistentes(DadosInconsistentesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), 400));
    }

}
