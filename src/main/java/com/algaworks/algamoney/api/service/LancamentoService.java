package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.exception.AlgaMoneyExceptionHandler;
import com.algaworks.algamoney.api.exception.PessoaInexistenteOuInativaException;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository repositoryPessoa;

    public Lancamento save(Lancamento lancamento) {
        Pessoa pessoa = repositoryPessoa.findOne(lancamento.getPessoa().getId());
        if (pessoa == null || pessoa.isInativo()){
            throw new PessoaInexistenteOuInativaException();
        }
        return repository.save(lancamento);
    }

    public Lancamento atualizar(Long codigo, Lancamento lancamento) {
        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
        if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
            validarPessoa(lancamento);
        }

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");

        return repository.save(lancamentoSalvo);
    }

    private void validarPessoa(Lancamento lancamento) {
        Pessoa pessoa = null;
        if (lancamento.getPessoa().getId() != null) {
            pessoa = repositoryPessoa.findOne(lancamento.getPessoa().getId());
        }

        if (pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
    }

    private Lancamento buscarLancamentoExistente(Long codigo) {
        Lancamento lancamentoSalvo = repository.findOne(codigo);
        if (lancamentoSalvo == null) {
            throw new IllegalArgumentException();
        }
        return lancamentoSalvo;
    }
}
