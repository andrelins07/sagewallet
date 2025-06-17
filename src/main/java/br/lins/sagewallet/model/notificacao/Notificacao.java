package br.lins.sagewallet.model.notificacao;

import br.lins.sagewallet.model.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    private LocalDate dataCriacao;

    @JoinColumn(name = "usuario_id")
    @ManyToOne
    private Usuario usuario;

    public Notificacao(Usuario usuario, String mensagem) {
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
