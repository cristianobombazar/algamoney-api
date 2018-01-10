package com.algaworks.algamoney.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@ControllerAdvice //observa a aplicação
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String messageUser      = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.getCause().toString();
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> createErrorList(BindingResult bindingResult){
        List<Error> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach( fieldError -> {
            String messageUser      = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String messageDeveloper = fieldError.toString();
            errors.add(new Error(messageUser, messageDeveloper));
        });
        return errors;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
        String messageUser      = messageSource.getMessage("recurso.nao-encontrado",null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString();
        return handleExceptionInternal(ex, Arrays.asList(new Error(messageUser, messageDeveloper)), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    class Error{

        private String messageUser;
        private String messageDeveloper;

        public Error(String messageUser, String messageDeveloper) {
            this.messageUser = messageUser;
            this.messageDeveloper = messageDeveloper;
        }

        public String getMessageUser() {
            return messageUser;
        }

        public void setMessageUser(String messageUser) {
            this.messageUser = messageUser;
        }

        public String getMessageDeveloper() {
            return messageDeveloper;
        }

        public void setMessageDeveloper(String messageDeveloper) {
            this.messageDeveloper = messageDeveloper;
        }
    }
}