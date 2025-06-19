package br.lins.sagewallet.dto;

import br.lins.sagewallet.model.lancamento.Lancamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoResponseDTO(
        @NotNull Long id,
        @NotBlank String descricao,
        @NotNull LocalDate data,
        @NotNull BigDecimal valor,
        @NotBlank String categoria,
        @NotBlank String tipo,
        @NotBlank String usuario) {

    public LancamentoResponseDTO(Lancamento lancamento){
        this(lancamento.getId(),
                lancamento.getDescricao(),
                lancamento.getData(),
                lancamento.getValor(),
                lancamento.getCategoria().getNome(),
                lancamento.getTipo().name(),
                lancamento.getUsuario().getNome()
        );
    }
}

