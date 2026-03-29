package com.guifroes1984.gastosPessoais.dto;

import java.math.BigDecimal;

public class ResumoResponse {

	private BigDecimal totalReceitas;
	private BigDecimal totalDespesas;
	private BigDecimal saldo;

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

}
