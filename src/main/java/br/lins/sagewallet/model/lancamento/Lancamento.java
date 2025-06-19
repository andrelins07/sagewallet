package br.lins.sagewallet.model.lancamento;

import br.lins.sagewallet.dto.LancamentoRequestDTO;
import br.lins.sagewallet.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_lancamentos")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String descricao;

    @NotNull
    private LocalDate data;

    @NotNull
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoLancamento tipo;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Lancamento(){

    }

    public Lancamento(LancamentoRequestDTO lancamento, Usuario usuario, Categoria categoria){
        this.descricao = lancamento.descricao();
        this.data = lancamento.data();
        this.valor = lancamento.valor();
        this.categoria = categoria;
        this.tipo = lancamento.tipo();
        this.usuario = usuario;
    }

    public void atualizar(LancamentoRequestDTO lancamento, Usuario usuario, Categoria categoria){
        this.descricao = lancamento.descricao();
        this.data = lancamento.data();
        this.valor = lancamento.valor();
        this.categoria = categoria;
        this.tipo = lancamento.tipo();
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
