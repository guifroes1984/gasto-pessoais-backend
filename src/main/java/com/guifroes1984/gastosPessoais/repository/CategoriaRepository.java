package com.guifroes1984.gastosPessoais.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guifroes1984.gastosPessoais.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
