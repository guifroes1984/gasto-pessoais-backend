package com.guifroes1984.gastosPessoais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public String login(String email, String senha) {
		
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
		
		if (!usuario.getSenha().equals(senha)) {
			throw new CredenciaisInvalidasException("Email ou senha inválidos");
		}
		
		return jwtUtil.gerarToken(email);
		
	}

}
