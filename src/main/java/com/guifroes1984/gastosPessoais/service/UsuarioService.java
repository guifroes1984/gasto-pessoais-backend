package com.guifroes1984.gastosPessoais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guifroes1984.gastosPessoais.exception.EmailJaCadastradoException;
import com.guifroes1984.gastosPessoais.model.Usuario;
import com.guifroes1984.gastosPessoais.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Usuario salvar(Usuario usuario) {

		if (repository.findByEmail(usuario.getEmail()).isPresent()) {
			throw new EmailJaCadastradoException("Email já cadastrado");
		}

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		return repository.save(usuario);
	}

}