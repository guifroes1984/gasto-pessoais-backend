package com.guifroes1984.gastosPessoais.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LancamentoRequest {

	@NotBlank(message = "descricao é obrigatória")
	private String descricao;

	@NotNull(message = "valor é obrigatório")
	private BigDecimal valor;

	@NotNull(message = "tipo é obrigatório")
	private String tipo;

	@NotNull(message = "data é obrigatória")
	private LocalDate data;

	//@NotNull(message = "usuarioId é obrigatório")
	//private Long usuarioId;

	@NotNull(message = "categoriaId é obrigatório")
	private Long categoriaId;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	//public Long getUsuarioId() {
	//	return usuarioId;
	//}

	//public void setUsuarioId(Long usuarioId) {
	//	this.usuarioId = usuarioId;
	//}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

}
