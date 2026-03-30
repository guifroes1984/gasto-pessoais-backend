package com.guifroes1984.gastosPessoais.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.dto.LancamentoRequest;
import com.guifroes1984.gastosPessoais.dto.LancamentoResponse;
import com.guifroes1984.gastosPessoais.dto.ResumoCategoriaDTO;
import com.guifroes1984.gastosPessoais.dto.ResumoResponse;
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
	public ResponseEntity<List<LancamentoResponse>> listarTodos() {
		return ResponseEntity.ok(service.listarPorUsuario());
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
	public ResponseEntity<List<ResumoCategoriaDTO>> resumoPorCategoria(@RequestAttribute("usuarioId") Long usuarioId) {
		return ResponseEntity.ok(service.resumoPorCategoria(usuarioId));
	}

}
