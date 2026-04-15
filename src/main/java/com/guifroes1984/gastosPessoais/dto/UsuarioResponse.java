package com.guifroes1984.gastosPessoais.dto;

import java.util.List;

public class UsuarioResponse {

	private Long id;
	private String nome;
	private String email;
	private List<String> roles;

	public UsuarioResponse() {
	}

	public UsuarioResponse(Long id, String nome, String email, List<String> roles) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
