package br.lins.sagewallet.dto;

import jakarta.validation.constraints.NotNull;

public record CompartilhamentoRequestDTO(
        @NotNull Integer idRemetente,
        @NotNull Integer idDestinatario)
{
}
