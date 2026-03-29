package com.guifroes1984.gastosPessoais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.dto.LancamentoRequest;
import com.guifroes1984.gastosPessoais.dto.LancamentoResponse;
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

}
