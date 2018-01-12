//package com.algaworks.algamoney.api.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("admin")
//            .password("admin")
//            .roles("ROLES");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/categoria") //libera apenas categoria
//                .permitAll() //permite todas as requisições para categoria
//                .anyRequest()
//                .authenticated()
//                .and()
//            .httpBasic().and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//            .csrf().disable();
//
//    }
//}
