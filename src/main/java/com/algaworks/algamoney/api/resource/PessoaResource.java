package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    @Autowired
    private PessoaRepository repository;

    @PostMapping
    public ResponseEntity<Pessoa> save(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        pessoa = repository.save(pessoa);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(pessoa.getId())
                .toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(pessoa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> find(@PathVariable Long id) {
        Pessoa pessoa = repository.findOne(id);
        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }

}
