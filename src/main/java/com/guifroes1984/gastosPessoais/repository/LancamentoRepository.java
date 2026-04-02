package com.guifroes1984.gastosPessoais.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.guifroes1984.gastosPessoais.dto.ResumoCategoriaDTO;
import com.guifroes1984.gastosPessoais.enuns.TipoLancamento;
import com.guifroes1984.gastosPessoais.model.Lancamento;
import com.guifroes1984.gastosPessoais.model.Usuario;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findByUsuario(Usuario usuario);

	List<Lancamento> findByUsuarioAndDataBetween(Usuario usuario, LocalDate dataInicio, LocalDate dataFim);

	Page<Lancamento> findByUsuario(Usuario usuario, Pageable pageable);

	Page<Lancamento> findByUsuarioAndTipoAndDataBetween(Usuario usuario, TipoLancamento tipo, LocalDate inicio,
			LocalDate fim, Pageable pageable);
	
	Optional<Lancamento> findByIdAndUsuario(Long id, Usuario usuario);

	@Query("""
			    SELECT new com.guifroes1984.gastosPessoais.dto.ResumoCategoriaDTO(
			        c.nome,
			        COALESCE(SUM(l.valor), 0)
			    )
			    FROM Lancamento l
			    JOIN l.categoria c
			    WHERE l.usuario.id = :usuarioId
			    AND l.tipo = :tipo
			    AND l.data BETWEEN :inicio AND :fim
			    GROUP BY c.nome
			""")
	List<ResumoCategoriaDTO> resumoPorCategoria(Long usuarioId, TipoLancamento tipo, LocalDate inicio, LocalDate fim);

	@Query("""
			    SELECT COALESCE(SUM(l.valor), 0)
			    FROM Lancamento l
			    WHERE l.usuario.id = :usuarioId
			    AND l.tipo = :tipo
			    AND l.data BETWEEN :inicio AND :fim
			""")
	BigDecimal somarPorTipo(Long usuarioId, TipoLancamento tipo, LocalDate inicio, LocalDate fim);

}
