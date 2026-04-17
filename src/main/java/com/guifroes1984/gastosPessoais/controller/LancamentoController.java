package com.guifroes1984.gastosPessoais.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.dto.DashboardDTO;
import com.guifroes1984.gastosPessoais.dto.LancamentoRequest;
import com.guifroes1984.gastosPessoais.dto.LancamentoResponse;
import com.guifroes1984.gastosPessoais.dto.ResumoCategoriaDTO;
import com.guifroes1984.gastosPessoais.dto.ResumoResponse;
import com.guifroes1984.gastosPessoais.enuns.TipoLancamento;
import com.guifroes1984.gastosPessoais.service.LancamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Lançamentos", description = "Gerenciamento de receitas e despesas")
@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService service;

	@Operation(summary = "Cadastrar lançamento", description = "Cria um novo lançamento (receita ou despesa)")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Lançamento criado com sucesso"),
	@ApiResponse(responseCode = "400", description = "Dados inválidos") })
	@PostMapping
	public ResponseEntity<LancamentoResponse> salvar(@RequestBody @Valid LancamentoRequest request) {
		return ResponseEntity.ok(service.salvar(request));
	}

	@Operation(summary = "Listar lançamentos do usuário", description = "Retorna lançamentos paginados do usuário autenticado")
	@GetMapping
	public ResponseEntity<Page<LancamentoResponse>> listarTodos(Pageable pageable) {
		return ResponseEntity.ok(service.listarPorUsuario(pageable));
	}

	@Operation(summary = "Atualizar lançamento")
	@ApiResponse(responseCode = "200", description = "Lançamento atualizado")
	@PutMapping("/{id}")
	public ResponseEntity<LancamentoResponse> atualizar(@PathVariable Long id,
			@RequestBody @Valid LancamentoRequest request) {
		return ResponseEntity.ok(service.atualizar(id, request));
	}

	@Operation(summary = "Deletar lançamento")
	@ApiResponse(responseCode = "204", description = "Lançamento removido")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {

		service.deletar(id);

		return ResponseEntity.noContent().build();

	}

	@Operation(summary = "Resumo financeiro", description = "Retorna resumo geral de receitas e despesas")
	@GetMapping("/resumo")
	public ResponseEntity<ResumoResponse> resumo() {
		return ResponseEntity.ok(service.resumo());
	}

	@Operation(summary = "Listar por período", description = "Retorna lançamentos entre duas datas")
	@GetMapping("/periodo")
	public ResponseEntity<List<LancamentoResponse>> listarPorPeriodo(
			@Parameter(description = "Data inicial (yyyy-MM-dd)") @RequestParam LocalDate dataInicio,

			@Parameter(description = "Data final (yyyy-MM-dd)") @RequestParam LocalDate dataFim) {

		return ResponseEntity.ok(service.listarPorPeriodo(dataInicio, dataFim));
	}

	@Operation(summary = "Resumo por categoria", description = "Agrupa lançamentos por categoria dentro de um período")
	@GetMapping("/resumo/categorias")
	public ResponseEntity<List<ResumoCategoriaDTO>> resumoPorCategoria(
			@Parameter(description = "Data inicial") @RequestParam LocalDate inicio,

			@Parameter(description = "Data final") @RequestParam LocalDate fim) {

		return ResponseEntity.ok(service.resumoPorCategoria(inicio, fim));
	}

	@Operation(summary = "Dashboard financeiro", description = "Retorna dados consolidados para dashboard")
	@GetMapping("/dashboard")
	public ResponseEntity<DashboardDTO> dashboard(
			@Parameter(description = "Data inicial") @RequestParam LocalDate inicio,

			@Parameter(description = "Data final") @RequestParam LocalDate fim) {

		return ResponseEntity.ok(service.dashboard(inicio, fim));
	}

	@Operation(summary = "Filtro avançado de lançamentos", description = "Filtra por tipo e período com paginação")
	@GetMapping("/filtro")
	public ResponseEntity<Page<LancamentoResponse>> listarComFiltro(

			@Parameter(description = "Tipo: RECEITA ou DESPESA") @RequestParam(required = false) TipoLancamento tipo,

			@Parameter(description = "Data inicial") @RequestParam(required = false) LocalDate inicio,

			@Parameter(description = "Data final") @RequestParam(required = false) LocalDate fim,

			Pageable pageable) {

		return ResponseEntity.ok(service.listarComFiltro(tipo, inicio, fim, pageable));
	}

}
