package br.lins.sagewallet.service;

import br.lins.sagewallet.model.indicadores.Indicadores;
import br.lins.sagewallet.model.lancamento.Categoria;
import br.lins.sagewallet.model.lancamento.Lancamento;
import br.lins.sagewallet.model.lancamento.TipoLancamento;
import br.lins.sagewallet.repository.CategoriaRepository;
import br.lins.sagewallet.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class IndicadoresService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Indicadores calcularIndicadores(Integer id) {

        List<Lancamento> lancamentos = lancamentoRepository.findByUsuarioId(id);
        List<Categoria> categorias = categoriaRepository.findAll();

        Indicadores indicadores = new Indicadores();

        BigDecimal totalReceitas = lancamentos.stream()
                .filter(l -> l.getTipo() == TipoLancamento.RECEITA)
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = lancamentos.stream()
                .filter(l -> l.getTipo() == TipoLancamento.DESPESA)
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        indicadores.setTotalReceitas(totalReceitas);
        indicadores.setTotaLDespesas(totalDespesas);
        indicadores.setSaldoDisponivel(totalReceitas.subtract(totalDespesas));

        List<Map<String, BigDecimal>> gastosPorCategoria = new ArrayList<>();

        for (Categoria categoria : categorias) {

            BigDecimal totalCategoria = lancamentos.stream()
                    .filter(l -> l.getCategoria().getId().equals(categoria.getId()))
                    .map(Lancamento::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            gastosPorCategoria.add(Map.of(categoria.getNome(), totalCategoria));
        }

        indicadores.setGastosPorCategoria(gastosPorCategoria);

        return indicadores;
    }
}
