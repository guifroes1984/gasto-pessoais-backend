package com.guifroes1984.gastosPessoais.exception;

public class CredenciaisInvalidasException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}
