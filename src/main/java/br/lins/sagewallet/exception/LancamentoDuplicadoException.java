package br.lins.sagewallet.exception;

public class LancamentoDuplicadoException extends RuntimeException {

    public LancamentoDuplicadoException() {
        super("Lançamento já cadastrado no sistema");
    }

}
