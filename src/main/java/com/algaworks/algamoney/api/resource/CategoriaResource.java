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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/categoria")
//@CrossOrigin(maxAge = 10)
public class CategoriaResource {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    //@CrossOrigin(maxAge = 10) //SO PODE FAZER REQUISIÇÃO PARA ESSE RECURSO A CADA 10 SEGUNDOS
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasAnyScope('read')")
    public List<Categoria> findAll(){
        return repository.findAll();
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasAnyScope('write')")
    public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        categoria = repository.save(categoria);
        publisher.publishEvent(new ResourceEvent(this, response, categoria.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasAnyScope('read')")
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


/*	@GetMapping("/outro")
	public List<Categoria> findAll(){
		return repository.findAll();
	}
*/
}