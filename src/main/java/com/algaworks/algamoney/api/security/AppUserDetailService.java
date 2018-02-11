package com.algaworks.algamoney.api.security;

import com.algaworks.algamoney.api.model.Usuario;
import com.algaworks.algamoney.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOption =  repository.findByEmail(email);
        Usuario usuario = usuarioOption.orElseThrow( () -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
        return new User(email, usuario.getSenha(), getPermission(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermission(Usuario usuario) {
        Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = new HashSet<>();
        usuario.getPermissoes().forEach( p -> simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
        return simpleGrantedAuthoritySet;
    }
}
