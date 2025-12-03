package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    // Obtener usuario actual autenticado
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<Usuario> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }

    // HU08 - Buscar usuarios
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Usuario>> buscarUsuarios(@RequestParam String query) {
        // Búsqueda eficiente usando query de base de datos
        // Esto evita cargar todos los usuarios en memoria
        List<Usuario> usuarios = usuarioRepository.buscarPorNombreApellidoOEmail(query);

        // No exponer contraseñas en resultados
        usuarios.forEach(u -> u.setPassword(null));

        return ResponseEntity.ok(usuarios);
    }

    // Obtener todos los usuarios (Solo ADMIN)
    @GetMapping

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // HU04 - Obtener perfil de usuario
    @GetMapping("/{id}")

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")

    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }

    // HU04 - Editar perfil
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        // SEGURIDAD: Verificar que el usuario autenticado solo pueda editar su propio perfil
        // a menos que sea ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailAutenticado = authentication.getName();

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // IMPORTANTE: Prevenir IDOR (Insecure Direct Object Reference)
        // Solo el mismo usuario o un ADMIN pueden modificar el perfil
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!usuario.getEmail().equals(emailAutenticado) && !isAdmin) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Actualizar campos (sin modificar password ni roles)
        if (usuarioActualizado.getPrimerNombre() != null) usuario.setPrimerNombre(usuarioActualizado.getPrimerNombre());
        if (usuarioActualizado.getPrimerApellido() != null) usuario.setPrimerApellido(usuarioActualizado.getPrimerApellido());
        if (usuarioActualizado.getPais() != null) usuario.setPais(usuarioActualizado.getPais());
        if (usuarioActualizado.getCiudad() != null) usuario.setCiudad(usuarioActualizado.getCiudad());
        if (usuarioActualizado.getFechaNacimiento() != null) usuario.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
        if (usuarioActualizado.getDescripcion() != null) usuario.setDescripcion(usuarioActualizado.getDescripcion());
        if (usuarioActualizado.getFotoPerfil() != null) usuario.setFotoPerfil(usuarioActualizado.getFotoPerfil());

        Usuario saved = usuarioRepository.save(usuario);

        // No exponer la contraseña en la respuesta
        saved.setPassword(null);

        return ResponseEntity.ok(saved);
    }

    // Eliminar usuario (Solo ADMIN)
    @DeleteMapping("/{id}")

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")


    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
