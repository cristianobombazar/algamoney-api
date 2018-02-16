package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa update(Long id, Pessoa pessoa){
        Pessoa pessoaSalva = find(id);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
        return repository.save(pessoaSalva);
    }

    public void updatePartial(Long id, Boolean ativo){
        Pessoa pessoa = find(id);
        pessoa.setAtivo(ativo);
        repository.save(pessoa);
    }

    Pessoa find(Long id) {
        Pessoa pessoaSalva = repository.findOne(id);
        if (pessoaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }

    public Pessoa buscarPessoaPeloCodigo(Long codigo) {
        Pessoa pessoaSalva = repository.findOne(codigo);
        if (pessoaSalva == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }

    public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable){
        return repository.findByNomeContaining(nome, pageable);
    }

}
