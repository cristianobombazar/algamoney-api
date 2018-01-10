package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceEvent;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Lancamento> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> find(@PathVariable Long id){
        Lancamento lancamento = repository.findOne(id);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        lancamento = repository.save(lancamento);
        publisher.publishEvent(new ResourceEvent(this, response, lancamento.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);

    }
}
