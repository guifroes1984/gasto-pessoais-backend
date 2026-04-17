package com.guifroes1984.gastosPessoais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.model.Categoria;
import com.guifroes1984.gastosPessoais.repository.CategoriaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categorias", description = "Gerenciamento de categorias de lançamentos")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository repository;

	@Operation(summary = "Cadastrar nova categoria", description = "Cria uma nova categoria para classificação de receitas ou despesas")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
	@ApiResponse(responseCode = "400", description = "Dados inválidos") })
	@PostMapping
	public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria) {
		return ResponseEntity.ok(repository.save(categoria));
	}

	@Operation(summary = "Listar todas as categorias", description = "Retorna todas as categorias cadastradas")
	@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
	@GetMapping
	public List<Categoria> listar() {
		return repository.findAll();
	}
}
