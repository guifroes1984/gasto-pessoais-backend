package com.guifroes1984.gastosPessoais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guifroes1984.gastosPessoais.dto.LoginRequest;
import com.guifroes1984.gastosPessoais.exception.CredenciaisInvalidasException;
import com.guifroes1984.gastosPessoais.exception.RecursoNaoEncontradoException;
import com.guifroes1984.gastosPessoais.model.Usuario;
import com.guifroes1984.gastosPessoais.repository.UsuarioRepository;
import com.guifroes1984.gastosPessoais.security.JwtUtil;

@Service
public class AuthService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public String login(LoginRequest request) {
		
		Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
		
		if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
			throw new CredenciaisInvalidasException("Senha inválida");
		}
		
		return jwtUtil.gerarToken(usuario);
		
	}

}
