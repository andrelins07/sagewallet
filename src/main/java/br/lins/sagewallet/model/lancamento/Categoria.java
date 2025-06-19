package br.lins.sagewallet.model.lancamento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    public void atualizar(Categoria categoriaAtualziada) {
        this.nome = categoriaAtualziada.getNome();
        this.descricao = categoriaAtualziada.getDescricao();
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
