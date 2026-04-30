package com.guifroes1984.gastosPessoais.exception;

public class CategoriaJaExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CategoriaJaExisteException(String mensagem) {
		super(mensagem);
	}
}
