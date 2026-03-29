package com.guifroes1984.gastosPessoais.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.guifroes1984.gastosPessoais.dto.LancamentoRequest;
import com.guifroes1984.gastosPessoais.dto.LancamentoResponse;
import com.guifroes1984.gastosPessoais.enuns.TipoLancamento;
import com.guifroes1984.gastosPessoais.exception.RecursoNaoEncontradoException;
import com.guifroes1984.gastosPessoais.model.Categoria;
import com.guifroes1984.gastosPessoais.model.Lancamento;
import com.guifroes1984.gastosPessoais.model.Usuario;
import com.guifroes1984.gastosPessoais.repository.CategoriaRepository;
import com.guifroes1984.gastosPessoais.repository.LancamentoRepository;
import com.guifroes1984.gastosPessoais.repository.UsuarioRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public LancamentoResponse salvar(LancamentoRequest request) {
		
		String email = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();

		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

		Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

		Lancamento lancamento = new Lancamento();
		lancamento.setDescricao(request.getDescricao());
		lancamento.setValor(request.getValor());
		lancamento.setTipo(TipoLancamento.valueOf(request.getTipo()));
		lancamento.setData(request.getData());
		lancamento.setUsuario(usuario);
		lancamento.setCategoria(categoria);

		Lancamento salvo = repository.save(lancamento);

		return toResponse(salvo);
	}

	public List<Lancamento> listarPorUsuario(Long usuarioId) {
		return repository.findByUsuarioId(usuarioId);
	}

	private LancamentoResponse toResponse(Lancamento lanc) {
		LancamentoResponse response = new LancamentoResponse();
		response.setId(lanc.getId());
		response.setDescricao(lanc.getDescricao());
		response.setValor(lanc.getValor());
		response.setTipo(lanc.getTipo().name());
		response.setData(lanc.getData());
		response.setCategoria(lanc.getCategoria().getNome());
		return response;
	}
}
