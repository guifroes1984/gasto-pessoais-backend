package com.guifroes1984.gastosPessoais.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

		List<String> mensagens = ex.getBindingResult().getFieldErrors().stream().map(erro -> erro.getDefaultMessage())
				.toList();

		ApiError erro = new ApiError(400, "Erro de validação", mensagens.toString());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
