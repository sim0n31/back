package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
<<<<<<< HEAD
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
=======
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

<<<<<<< HEAD
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
=======
    // HU08 - Buscar usuarios
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    public ResponseEntity<List<Usuario>> buscarUsuarios(@RequestParam String query) {
        // Búsqueda simple por nombre o email (puedes mejorar con especificaciones)
        List<Usuario> usuarios = usuarioRepository.findAll().stream()
            .filter(u ->
                (u.getPrimerNombre() != null && u.getPrimerNombre().toLowerCase().contains(query.toLowerCase())) ||
                (u.getPrimerApellido() != null && u.getPrimerApellido().toLowerCase().contains(query.toLowerCase())) ||
                (u.getEmail() != null && u.getEmail().toLowerCase().contains(query.toLowerCase()))
            )
            .toList();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener todos los usuarios (Solo ADMIN)
    @GetMapping
<<<<<<< HEAD
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
=======
    @PreAuthorize("hasRole('ADMIN')")
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // HU04 - Obtener perfil de usuario
    @GetMapping("/{id}")
<<<<<<< HEAD
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
=======
    @PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }

    // HU04 - Editar perfil
    @PutMapping("/{id}")
<<<<<<< HEAD
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
=======
    @PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar campos (sin modificar password ni roles)
        if (usuarioActualizado.getPrimerNombre() != null) usuario.setPrimerNombre(usuarioActualizado.getPrimerNombre());
        if (usuarioActualizado.getPrimerApellido() != null) usuario.setPrimerApellido(usuarioActualizado.getPrimerApellido());
        if (usuarioActualizado.getPais() != null) usuario.setPais(usuarioActualizado.getPais());
        if (usuarioActualizado.getCiudad() != null) usuario.setCiudad(usuarioActualizado.getCiudad());
        if (usuarioActualizado.getFechaNacimiento() != null) usuario.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
        if (usuarioActualizado.getDescripcion() != null) usuario.setDescripcion(usuarioActualizado.getDescripcion());
        if (usuarioActualizado.getFotoPerfil() != null) usuario.setFotoPerfil(usuarioActualizado.getFotoPerfil());

        Usuario saved = usuarioRepository.save(usuario);
        return ResponseEntity.ok(saved);
    }

    // Eliminar usuario (Solo ADMIN)
    @DeleteMapping("/{id}")
<<<<<<< HEAD
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
=======
    @PreAuthorize("hasRole('ADMIN')")
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
