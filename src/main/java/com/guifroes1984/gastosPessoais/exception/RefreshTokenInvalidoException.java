package com.guifroes1984.gastosPessoais.exception;

public class RefreshTokenInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RefreshTokenInvalidoException(String mensagem) {
        super(mensagem);
    }

}
