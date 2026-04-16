package com.guifroes1984.gastosPessoais.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guifroes1984.gastosPessoais.dto.LoginRequest;
import com.guifroes1984.gastosPessoais.dto.LoginResponse;
import com.guifroes1984.gastosPessoais.exception.CredenciaisInvalidasException;
import com.guifroes1984.gastosPessoais.exception.RecursoNaoEncontradoException;
import com.guifroes1984.gastosPessoais.model.RefreshToken;
import com.guifroes1984.gastosPessoais.model.Usuario;
import com.guifroes1984.gastosPessoais.repository.RefreshTokenRepository;
import com.guifroes1984.gastosPessoais.repository.UsuarioRepository;
import com.guifroes1984.gastosPessoais.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public LoginResponse login(LoginRequest request) {

		Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

		if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
			throw new CredenciaisInvalidasException("Senha inválida");
		}

		String accessToken = jwtUtil.gerarToken(usuario);
		String refreshToken = gerarRefreshToken(usuario);

		return new LoginResponse(accessToken, refreshToken);
	}

	public String gerarRefreshToken(Usuario usuario) {

		String token = UUID.randomUUID().toString();

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(token);
		refreshToken.setUsuario(usuario);
		refreshToken.setExpiracao(LocalDateTime.now().plusDays(7));

		refreshTokenRepository.save(refreshToken);

		return token;

	}

	public LoginResponse refresh(String token) {

		RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Refresh token inválido"));

		if (refreshToken.getExpiracao().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Refresh token expirado");
		}

		Usuario usuario = refreshToken.getUsuario();

		String novoAccessToken = jwtUtil.gerarToken(usuario);

		return new LoginResponse(novoAccessToken, token);
	}

}
