package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.config.property.ProjectProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

    @Autowired
    private ProjectProperty projectProperty;

    @DeleteMapping("/revoke")
    public void revoke(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(projectProperty.getSecurity().isEnableHttps()); //TODO: Em produção será true
        cookie.setPath(request.getContextPath()+"/oauth/token");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
