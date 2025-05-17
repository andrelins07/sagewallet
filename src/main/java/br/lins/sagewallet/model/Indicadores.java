package br.lins.sagewallet.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Indicadores {

    private BigDecimal totalReceitas;

    private BigDecimal totaLDespesas;

    private BigDecimal saldoDisponivel;

    private List<Map<String, BigDecimal>> gastosPorCategoria;

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(BigDecimal totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public BigDecimal getTotaLDespesas() {
        return totaLDespesas;
    }

    public void setTotaLDespesas(BigDecimal totaLDespesas) {
        this.totaLDespesas = totaLDespesas;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public List<Map<String, BigDecimal>> getGastosPorCategoria() {
        return gastosPorCategoria;
    }

    public void setGastosPorCategoria(List<Map<String, BigDecimal>> gastosPorCategoria) {
        this.gastosPorCategoria = gastosPorCategoria;
    }
}
