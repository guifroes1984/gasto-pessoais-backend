package com.guifroes1984.gastosPessoais.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guifroes1984.gastosPessoais.model.Lancamento;
import com.guifroes1984.gastosPessoais.model.Usuario;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findByUsuario(Usuario usuario);

	List<Lancamento> findByUsuarioAndDataBetween(Usuario usuario, LocalDate dataInicio, LocalDate dataFim);

}
