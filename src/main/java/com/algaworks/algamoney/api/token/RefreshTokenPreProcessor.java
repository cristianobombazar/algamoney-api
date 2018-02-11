package com.algaworks.algamoney.api.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.sql.ParameterMetaData;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) //PRIMEIRA A SER EXECUTADA
public class RefreshTokenPreProcessor implements Filter {

    public static final String URI_TOKEN         = "/oauth/token";
    public static final String URI_REFRESH_TOKEN = "refresh_token";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getRequestURI().equalsIgnoreCase(RefreshTokenPreProcessor.URI_TOKEN) &&  req.getParameter("grant_type").equalsIgnoreCase(RefreshTokenPreProcessor.URI_REFRESH_TOKEN) && req.getCookies() != null){
            for(Cookie cookie : req.getCookies()){
                if (cookie.getName().equalsIgnoreCase("refreshToken")){
                    String refreshToken = cookie.getValue();
                    req = new MyServletRequestWrapper(req, refreshToken);
                }
            }
        }
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {

    }

    private class MyServletRequestWrapper extends HttpServletRequestWrapper{

        private String refreshToken;

        public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put(RefreshTokenPreProcessor.URI_REFRESH_TOKEN, new String[]{refreshToken});
            map.setLocked(true);
            return map;
        }
    }
}
