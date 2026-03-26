package com.guifroes1984.gastosPessoais.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.gastosPessoais.model.Lancamento;
import com.guifroes1984.gastosPessoais.repository.LancamentoRepository;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    public Lancamento salvar(Lancamento lancamento) {
        return repository.save(lancamento);
    }

    public List<Lancamento> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
