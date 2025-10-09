package com.upc.molinachirinostp.security.services;

import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findAll().stream()
                .filter(x -> username.equals(x.getEmail()))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(u.getEmail()).password(u.getPassword()).authorities("USER").build();
    }
}
