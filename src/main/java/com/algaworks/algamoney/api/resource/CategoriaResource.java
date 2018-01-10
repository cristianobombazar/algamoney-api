package com.algaworks.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.algaworks.algamoney.api.event.ResourceEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/categoria")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Categoria> findAll(){
        return repository.findAll();
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        categoria = repository.save(categoria);
        publisher.publishEvent(new ResourceEvent(this, response, categoria.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Long id) {
        Categoria categoria = repository.findOne(id);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

/*
	@GetMapping
	public ResponseEntity<?> findAll(){
		List<Categoria> categorias = repository.findAll();
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
	}
*/

/*	@GetMapping("/outro")
	public List<Categoria> findAll(){
		return repository.findAll();
	}
*/
}