package br.lins.sagewallet.dto;

import br.lins.sagewallet.model.lancamento.Categoria;
import br.lins.sagewallet.model.lancamento.TipoLancamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoRequestDTO(
        @NotBlank String descricao,
        @NotNull LocalDate data,
        @NotNull BigDecimal valor,
        @NotNull Integer idCategoria,
        @NotNull TipoLancamento tipo,
        @NotNull Integer idUsuario
) { }
