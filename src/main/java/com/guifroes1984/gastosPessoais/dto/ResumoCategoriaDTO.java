package com.guifroes1984.gastosPessoais.dto;

import java.math.BigDecimal;

public class ResumoCategoriaDTO {

	private String categoria;
	private BigDecimal total;

	public ResumoCategoriaDTO(String categoria, BigDecimal total) {
		this.categoria = categoria;
		this.total = total;
	}

	public String getCategoria() {
		return categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}

}
