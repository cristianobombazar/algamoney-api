package com.algaworks.algamoney.api.event.listner;

import com.algaworks.algamoney.api.event.ResourceEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ResourceListner implements ApplicationListener<ResourceEvent> {

    @Override
    public void onApplicationEvent(ResourceEvent resourceEvent) {
        HttpServletResponse response = resourceEvent.getResponse();
        Long id = resourceEvent.getId();
        addHeaderToResponse(response, id);
    }

    void addHeaderToResponse(HttpServletResponse response, Long id) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(id)
                .toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

    //poderia ser criado um método apenas com a a notação @EventListener, sem estender ApplicationListner
}
