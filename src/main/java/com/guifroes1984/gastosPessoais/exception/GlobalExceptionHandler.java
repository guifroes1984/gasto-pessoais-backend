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

	@ExceptionHandler(CredenciaisInvalidasException.class)
	public ResponseEntity<ApiError> handleCredenciais(CredenciaisInvalidasException ex) {

		ApiError erro = new ApiError(401, "Credenciais inválidas", ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ApiError> handleNotFound(RecursoNaoEncontradoException ex) {

		ApiError erro = new ApiError(404, "Recurso não encontrado", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@ExceptionHandler(EmailJaCadastradoException.class)
	public ResponseEntity<ApiError> handleEmailJaCadastrado(EmailJaCadastradoException ex) {

		ApiError erro = new ApiError(400, "Email já cadastrado", ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeral(Exception ex) {

		ApiError erro = new ApiError(500, "Erro interno", "Ocorreu um erro inesperado");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}

	@ExceptionHandler(RefreshTokenInvalidoException.class)
	public ResponseEntity<ApiError> handleRefreshToken(RefreshTokenInvalidoException ex) {

		ApiError erro = new ApiError(HttpStatus.UNAUTHORIZED.value(), "Refresh token inválido", ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}

}
