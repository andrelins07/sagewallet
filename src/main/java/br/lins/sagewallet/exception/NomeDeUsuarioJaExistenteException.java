package br.lins.sagewallet.exception;

public class NomeDeUsuarioJaExistenteException extends RuntimeException {
    public NomeDeUsuarioJaExistenteException(String message) {
        super(message);
    }
    public NomeDeUsuarioJaExistenteException(){
        super("Nome de usuário já está em uso.");
    }
}
