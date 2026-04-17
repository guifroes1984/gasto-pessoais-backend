package com.guifroes1984.gastosPessoais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.dto.LoginRequest;
import com.guifroes1984.gastosPessoais.dto.LoginResponse;
import com.guifroes1984.gastosPessoais.dto.RefreshTokenRequest;
import com.guifroes1984.gastosPessoais.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação", description = "Operações de login e geração de tokens")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Operation(summary = "Realizar login", description = "Autentica o usuário e retorna accessToken e refreshToken")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
	@ApiResponse(responseCode = "401", description = "Credenciais inválidas") })
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@Operation(summary = "Gerar novo access token", description = "Recebe um refresh token válido e retorna um novo access token")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Token renovado com sucesso"),
	@ApiResponse(responseCode = "401", description = "Refresh token inválido ou expirado") })
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
	}

}
