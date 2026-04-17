package com.guifroes1984.gastosPessoais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.dto.UsuarioResponse;
import com.guifroes1984.gastosPessoais.enuns.Role;
import com.guifroes1984.gastosPessoais.model.Usuario;
import com.guifroes1984.gastosPessoais.repository.UsuarioRepository;
import com.guifroes1984.gastosPessoais.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários", description = "Operações de cadastro e listagem de usuários")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioService service;

	@Operation(summary = "Cadastrar novo usuário")
	@PostMapping
	public ResponseEntity<UsuarioResponse> salvar(@RequestBody Usuario usuario) {
		Usuario salvo = service.salvar(usuario);
		return ResponseEntity.ok(toResponse(salvo));
	}

	@Operation(summary = "Listar todos os usuários")
	@GetMapping
	public ResponseEntity<List<UsuarioResponse>> listar() {
		return ResponseEntity.ok(repository.findAll().stream().map(this::toResponse).toList());
	}

	private UsuarioResponse toResponse(Usuario usuario) {
		UsuarioResponse response = new UsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		if (usuario.getRoles() != null) {
			List<String> roles = usuario.getRoles().stream().map(Role::name).toList();

			response.setRoles(roles);
		}
		return response;
	}

}
