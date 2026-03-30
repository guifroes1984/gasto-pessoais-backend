package com.guifroes1984.gastosPessoais.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guifroes1984.gastosPessoais.dto.ResumoCategoriaDTO;
import com.guifroes1984.gastosPessoais.enuns.TipoLancamento;
import com.guifroes1984.gastosPessoais.model.Lancamento;
import com.guifroes1984.gastosPessoais.model.Usuario;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findByUsuario(Usuario usuario);

	List<Lancamento> findByUsuarioAndDataBetween(Usuario usuario, LocalDate dataInicio, LocalDate dataFim);

	@Query("""
			    SELECT new com.guifroes1984.gastosPessoais.dto.ResumoCategoriaDTO(
			        c.nome,
			        SUM(l.valor)
			    )
			    FROM Lancamento l
			    JOIN l.categoria c
			    WHERE l.usuario.id = :usuarioId
			    AND l.tipo = :tipo
			    GROUP BY c.nome
			""")
	List<ResumoCategoriaDTO> resumoPorCategoria(@Param("usuarioId") Long usuarioId, @Param("tipo") TipoLancamento tipo);

}
