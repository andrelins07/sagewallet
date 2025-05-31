package br.lins.sagewallet.exception;

public class EmailJaExistenteException extends RuntimeException {
    public EmailJaExistenteException(String message) {
        super(message);
    }
    public EmailJaExistenteException(){
        super("Email já está em uso.");
    }
}
