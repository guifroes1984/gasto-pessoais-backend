package com.guifroes1984.gastosPessoais.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.gastosPessoais.exception.CategoriaJaExisteException;
import com.guifroes1984.gastosPessoais.exception.RecursoNaoEncontradoException;
import com.guifroes1984.gastosPessoais.model.Categoria;
import com.guifroes1984.gastosPessoais.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria salvar(Categoria categoria) {

		if (repository.existsByNome(categoria.getNome())) {
			throw new CategoriaJaExisteException("Já existe uma categoria com esse nome");
		}

		return repository.save(categoria);
	}

	public List<Categoria> listar() {
		return repository.findAll();
	}

	public Categoria buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
	}

	public Categoria atualizar(Long id, Categoria categoria) {

		Categoria existente = buscarPorId(id);

		existente.setNome(categoria.getNome());

		return repository.save(existente);
	}

	public void deletar(Long id) {

		Categoria categoria = buscarPorId(id);

		repository.delete(categoria);
	}
}
