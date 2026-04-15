package com.guifroes1984.gastosPessoais.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService service;

	@PostMapping
	public ResponseEntity<LancamentoResponse> salvar(@RequestBody @Valid LancamentoRequest request) {
		return ResponseEntity.ok(service.salvar(request));
	}

	@GetMapping
	public ResponseEntity<Page<LancamentoResponse>> listarTodos(Pageable pageable) {
	    return ResponseEntity.ok(service.listarPorUsuario(pageable));
	}

	@PutMapping("/{id}")
	public ResponseEntity<LancamentoResponse> atualizar(@PathVariable Long id,
			@RequestBody @Valid LancamentoRequest request) {
		return ResponseEntity.ok(service.atualizar(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {

		service.deletar(id);

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/resumo")
	public ResponseEntity<ResumoResponse> resumo() {
		return ResponseEntity.ok(service.resumo());
	}

	@GetMapping("/periodo")
	public ResponseEntity<List<LancamentoResponse>> listarPorPeriodo(@RequestParam LocalDate dataInicio,
			@RequestParam LocalDate dataFim) {

		return ResponseEntity.ok(service.listarPorPeriodo(dataInicio, dataFim));
	}

	@GetMapping("/resumo/categorias")
	public ResponseEntity<List<ResumoCategoriaDTO>> resumoPorCategoria(@RequestParam LocalDate inicio,
			@RequestParam LocalDate fim) {
		return ResponseEntity.ok(service.resumoPorCategoria(inicio, fim));
	}

	@GetMapping("/dashboard")
	public ResponseEntity<DashboardDTO> dashboard(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {

		return ResponseEntity.ok(service.dashboard(inicio, fim));
	}

	@GetMapping("/filtro")
	public ResponseEntity<Page<LancamentoResponse>> listarComFiltro(@RequestParam(required = false) TipoLancamento tipo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
			Pageable pageable) {
		
		return ResponseEntity.ok(service.listarComFiltro(tipo, inicio, fim, pageable));

	}

}
