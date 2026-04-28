package com.guifroes1984.gastosPessoais.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.guifroes1984.gastosPessoais.dto.DashboardDTO;
import com.guifroes1984.gastosPessoais.dto.LancamentoRequest;
import com.guifroes1984.gastosPessoais.dto.LancamentoResponse;
import com.guifroes1984.gastosPessoais.dto.ResumoCategoriaDTO;
import com.guifroes1984.gastosPessoais.dto.ResumoResponse;
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

		Usuario usuario = getUsuarioLogado();

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

	public Page<LancamentoResponse> listarPorUsuario(Pageable pageable) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

		Page<Lancamento> pagina = repository.findByUsuario(usuario, pageable);

		return pagina.map(this::toResponse);
	}
	
	public LancamentoResponse buscarPorId(Long id) {
		Lancamento lancamento = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Lançamento não encontrado"));
		
		LancamentoResponse response = new LancamentoResponse();
		
		response.setId(lancamento.getId());
		response.setDescricao(lancamento.getDescricao());
	    response.setValor(lancamento.getValor());
	    response.setTipo(lancamento.getTipo().name());
	    response.setData(lancamento.getData());
	    response.setCategoriaId(lancamento.getCategoria().getId());
		
		return response;
	}

	public LancamentoResponse atualizar(Long id, LancamentoRequest request) {

		Usuario usuario = getUsuarioLogado();

		Lancamento lancamento = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Lancamento não encontrado"));

		if (!lancamento.getUsuario().getId().equals(usuario.getId())) {
			throw new RuntimeException("Acesso negado");
		}

		Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

		lancamento.setDescricao(request.getDescricao());
		lancamento.setValor(request.getValor());
		lancamento.setTipo(TipoLancamento.valueOf(request.getTipo()));
		lancamento.setData(request.getData());
		lancamento.setCategoria(categoria);

		Lancamento atualizado = repository.save(lancamento);

		return toResponse(atualizado);

	}

	public void deletar(Long id) {

		Usuario usuario = getUsuarioLogado();

		Lancamento lancamento = repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Lançamento não encontrado"));

		if (!lancamento.getUsuario().getId().equals(usuario.getId())) {
			throw new RuntimeException("Acesso negado");
		}

		repository.delete(lancamento);

	}

	public ResumoResponse resumo() {

		Usuario usuario = getUsuarioLogado();

		List<Lancamento> lancamentos = repository.findByUsuario(usuario);

		BigDecimal receitas = lancamentos.stream().filter(l -> l.getTipo() == TipoLancamento.RECEITA)
				.map(Lancamento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal despesas = lancamentos.stream().filter(l -> l.getTipo() == TipoLancamento.DESPESA)
				.map(Lancamento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

		ResumoResponse resumo = new ResumoResponse();
		resumo.setTotalReceitas(receitas);
		resumo.setTotalDespesas(despesas);
		resumo.setSaldo(receitas.subtract(despesas));

		return resumo;

	}

	public List<LancamentoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {

		Usuario usuario = getUsuarioLogado();

		List<Lancamento> lancamentos = repository.findByUsuarioAndDataBetween(usuario, dataInicio, dataFim);

		return lancamentos.stream().map(this::toResponse).toList();
	}

	public List<ResumoCategoriaDTO> resumoPorCategoria(LocalDate inicio, LocalDate fim) {
		Usuario usuario = getUsuarioLogado();

		return repository.resumoPorCategoria(usuario.getId(), TipoLancamento.DESPESA, inicio, fim);
	}

	public DashboardDTO dashboard(LocalDate inicio, LocalDate fim) {

		Usuario usuario = getUsuarioLogado();

		BigDecimal receitas = repository.somarPorTipo(usuario.getId(), TipoLancamento.RECEITA, inicio, fim);

		BigDecimal despesas = repository.somarPorTipo(usuario.getId(), TipoLancamento.DESPESA, inicio, fim);

		List<ResumoCategoriaDTO> categorias = repository.resumoPorCategoria(usuario.getId(), TipoLancamento.DESPESA,
				inicio, fim);

		DashboardDTO dto = new DashboardDTO();
		dto.setTotalReceitas(receitas);
		dto.setTotalDespesas(despesas);
		dto.setSaldo(receitas.subtract(despesas));
		dto.setDespesasPorCategoria(categorias);

		return dto;
	}

	public Page<LancamentoResponse> listarComFiltro(TipoLancamento tipo, LocalDate inicio, LocalDate fim,
			Pageable pageable) {

		Usuario usuario = getUsuarioLogado();

		Page<Lancamento> page;

		if (tipo != null && inicio != null && fim != null) {
			page = repository.findByUsuarioAndTipoAndDataBetween(usuario, tipo, inicio, fim, pageable);
		} else {
			page = repository.findByUsuario(usuario, pageable);
		}

		return page.map(this::toResponse);

	}

	private Usuario getUsuarioLogado() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

	}

	private LancamentoResponse toResponse(Lancamento lanc) {
		LancamentoResponse response = new LancamentoResponse();
		response.setId(lanc.getId());
		response.setDescricao(lanc.getDescricao());
		response.setValor(lanc.getValor());
		response.setTipo(lanc.getTipo().name());
		response.setData(lanc.getData());
		//response.setCategoria(lanc.getCategoria().getNome());
		return response;
	}
}
