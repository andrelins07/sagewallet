package br.lins.sagewallet.exception;

public class UsuarioDuplicadoException extends RuntimeException {

    public UsuarioDuplicadoException(String message) {
        super(message);
    }
    public UsuarioDuplicadoException() {
      super("Esse usuário já existe em nosso sistema");
    }
}
