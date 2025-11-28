package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // HU08 - Buscar usuarios
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // HU04 - Obtener perfil de usuario
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }

    // HU04 - Editar perfil
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
