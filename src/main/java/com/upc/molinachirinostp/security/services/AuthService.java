package com.upc.molinachirinostp.security.services;

import com.upc.molinachirinostp.entity.Role;
import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.RoleRepository;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import com.upc.molinachirinostp.security.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public Usuario register(Usuario u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        // Asignar rol ROLE_USER por defecto
        if (u.getRoles() == null || u.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(userRole);
            u.setRoles(roles);
        }

        return usuarioRepository.save(u);
    }

    public boolean resetPassword(String email, String newPassword) {
        return usuarioRepository.findByEmail(email).map(u -> {
            u.setPassword(passwordEncoder.encode(newPassword));
            usuarioRepository.save(u);
            return true;
        }).orElse(false);
    }

    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String principalName = authentication.getName();
        return jwtUtils.generateJwtToken(principalName);
    }
}
