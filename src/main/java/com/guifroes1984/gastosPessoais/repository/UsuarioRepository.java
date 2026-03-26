package com.guifroes1984.gastosPessoais.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guifroes1984.gastosPessoais.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
}
