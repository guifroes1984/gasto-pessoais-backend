package com.guifroes1984.gastosPessoais.exception;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;

    public ApiError(Integer status, String erro, String mensagem) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public String getMensagem() {
        return mensagem;
    }
}
