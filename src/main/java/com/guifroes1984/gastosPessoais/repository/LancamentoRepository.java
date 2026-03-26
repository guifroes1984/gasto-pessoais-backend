package com.guifroes1984.gastosPessoais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guifroes1984.gastosPessoais.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	List<Lancamento> findByUsuarioId(Long usuarioId);
}
