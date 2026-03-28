package com.guifroes1984.gastosPessoais.exception;

public class EmailJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}