package br.lins.sagewallet.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String message, LocalDateTime timestamp, int status) {}

