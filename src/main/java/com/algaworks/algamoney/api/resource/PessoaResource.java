package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
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

        @Autowired
        private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Pessoa> save(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        pessoa = repository.save(pessoa);
        publisher.publishEvent(new ResourceEvent(this, response, pessoa.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> find(@PathVariable Long id) {
        Pessoa pessoa = repository.findOne(id);
        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        repository.delete(id);
    }

}
