package com.guifroes1984.gastosPessoais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.model.Lancamento;
import com.guifroes1984.gastosPessoais.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService service;

    @PostMapping
    public ResponseEntity<Lancamento> salvar(@RequestBody Lancamento lancamento) {
        return ResponseEntity.ok(service.salvar(lancamento));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Lancamento>> listar(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorUsuario(id));
    }
}
