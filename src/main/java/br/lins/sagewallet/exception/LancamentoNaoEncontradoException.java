package br.lins.sagewallet.exception;

public class LancamentoNaoEncontradoException extends RuntimeException {

    public LancamentoNaoEncontradoException() {
        super("Lancamento não encontrado!");
    }
}
