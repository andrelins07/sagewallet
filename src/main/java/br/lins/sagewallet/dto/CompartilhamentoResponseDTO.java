package br.lins.sagewallet.dto;

import br.lins.sagewallet.model.compartilhamento.Compartilhamento;
import java.time.LocalDate;

public record CompartilhamentoResponseDTO(
        Integer id,
        String usuarioRemetente,
        String usuarioDestinatario,
        LocalDate dataSolicitacao,
        String status,
        LocalDate dataCompartilhamento
) {
    public CompartilhamentoResponseDTO(Compartilhamento compartilhamento) {
        this(
                compartilhamento.getId(),
                compartilhamento.getRemetente().getNome(),
                compartilhamento.getDestinatario().getNome(),
                compartilhamento.getDataSolicitacao(),
                compartilhamento.getStatus().name(),
                compartilhamento.getDataCompartilhamento()
        );
    }
}
