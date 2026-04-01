package com.guifroes1984.gastosPessoais.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {

	private BigDecimal totalReceitas;
	private BigDecimal totalDespesas;
	private BigDecimal saldo;
	private List<ResumoCategoriaDTO> despesasPorCategoria;

	public BigDecimal getTotalReceitas() {
		return totalReceitas;
	}

	public void setTotalReceitas(BigDecimal totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public BigDecimal getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(BigDecimal totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public List<ResumoCategoriaDTO> getDespesasPorCategoria() {
		return despesasPorCategoria;
	}

	public void setDespesasPorCategoria(List<ResumoCategoriaDTO> despesasPorCategoria) {
		this.despesasPorCategoria = despesasPorCategoria;
	}

}
