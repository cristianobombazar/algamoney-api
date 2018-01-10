package com.algaworks.algamoney.api.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long id;

    public ResourceEvent(Object source, HttpServletResponse response, Long id){
        super(source);
        this.response = response;
        this.id       = id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getId() {
        return id;
    }
}
