package br.lins.sagewallet.model.compartilhamento;

import br.lins.sagewallet.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tb_compartilhamentos_lancamentos")
public class Compartilhamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    @NotNull
    private Usuario remetente; //usuario que compartilha o perfil

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    @NotNull
    private Usuario destinatario; //usuario que recebe o compartilhamento

    private LocalDate dataCompartilhamento;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitacao status;

    private LocalDate dataSolicitacao;

    public Compartilhamento(){

    }

    public Compartilhamento(Usuario remetente, Usuario destinatario) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.status = EstadoSolicitacao.PENDENTE;
        this.dataSolicitacao = LocalDate.now();
    }

    public void responderSolicitacao(EstadoSolicitacao resposta) {

        this.status = resposta;

        if (resposta == EstadoSolicitacao.APROVADO){
            this.dataCompartilhamento = LocalDate.now();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(@NotNull Usuario remetente) {
        this.remetente = remetente;
    }

    public @NotNull Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(@NotNull Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public LocalDate getDataCompartilhamento() {
        return dataCompartilhamento;
    }

    public void setDataCompartilhamento(LocalDate dataCompartilhamento) {
        this.dataCompartilhamento = dataCompartilhamento;
    }

    public EstadoSolicitacao getStatus() {
        return status;
    }

    public void setStatus(EstadoSolicitacao status) {
        this.status = status;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
}
