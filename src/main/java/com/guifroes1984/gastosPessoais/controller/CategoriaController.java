package com.guifroes1984.gastosPessoais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.gastosPessoais.model.Categoria;
import com.guifroes1984.gastosPessoais.repository.CategoriaRepository;
import com.guifroes1984.gastosPessoais.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categorias", description = "Gerenciamento de categorias de lançamentos")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private CategoriaService service;

	@Operation(summary = "Cadastrar nova categoria", description = "Cria uma nova categoria para classificação de receitas ou despesas")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
	@ApiResponse(responseCode = "400", description = "Dados inválidos") })
	@PostMapping
	public ResponseEntity<Categoria> salvar(@RequestBody @Valid Categoria categoria) {
		return ResponseEntity.ok(repository.save(categoria));
	}

	@Operation(summary = "Listar todas as categorias", description = "Retorna todas as categorias cadastradas")
	@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
	@GetMapping
	public ResponseEntity<List<Categoria>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@Operation(summary = "Buscar categoria por ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
	@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@Operation(summary = "Atualizar categoria")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria atualizada"),
	@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long id,
			@RequestBody @Valid Categoria categoria) {
		return ResponseEntity.ok(service.atualizar(id, categoria));
	}

	@Operation(summary = "Deletar categoria")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Categoria removida"),
	@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
