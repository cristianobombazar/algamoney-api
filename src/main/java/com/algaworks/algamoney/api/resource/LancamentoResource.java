package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceEvent;
import com.algaworks.algamoney.api.exception.AlgaMoneyExceptionHandler;
import com.algaworks.algamoney.api.exception.PessoaInexistenteOuInativaException;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private LancamentoService service;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Lancamento> find(LancamentoFilter filter){
        return repository.find(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> find(@PathVariable Long id){
        Lancamento lancamento = repository.findOne(id);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        lancamento = service.save(lancamento);
        publisher.publishEvent(new ResourceEvent(this, response, lancamento.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex, WebRequest request){
        String messageUser      = messageSource.getMessage("pessoa.inexistente-ou-inativa",null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString();
        return ResponseEntity.badRequest().body(Arrays.asList(new AlgaMoneyExceptionHandler.Error(messageUser, messageDeveloper)));
    }
}
