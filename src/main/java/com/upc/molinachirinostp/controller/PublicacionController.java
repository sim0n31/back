package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Comentario;
import com.upc.molinachirinostp.entity.Publicacion;
import com.upc.molinachirinostp.entity.Reaccion;
import com.upc.molinachirinostp.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/publicaciones")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
public class PublicacionController {

    private final PublicacionService publicacionService;

    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    // HU20 - Crear publicación
    @PostMapping
    public ResponseEntity<Publicacion> crearPublicacion(@RequestBody Map<String, Object> request) {
        Long autorId = Long.valueOf(request.get("autorId").toString());
        String contenido = request.get("contenido").toString();
        String imagen = request.getOrDefault("imagen", "").toString();

        Publicacion publicacion = publicacionService.crearPublicacion(autorId, contenido, imagen);
        return ResponseEntity.ok(publicacion);
    }

    // Ver feed completo
    @GetMapping("/feed")
    public ResponseEntity<List<Publicacion>> getFeed() {
        List<Publicacion> publicaciones = publicacionService.getFeed();
        return ResponseEntity.ok(publicaciones);
    }

    // Ver publicaciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Publicacion>> getPublicacionesByUsuario(@PathVariable Long usuarioId) {
        List<Publicacion> publicaciones = publicacionService.getPublicacionesByUsuario(usuarioId);
        return ResponseEntity.ok(publicaciones);
    }

    // Ver publicaciones por autor
    @GetMapping("/autor/{usuarioId}")
    public ResponseEntity<List<Publicacion>> getPublicacionesByAutor(@PathVariable Long usuarioId) {
        List<Publicacion> publicaciones = publicacionService.getPorAutor(usuarioId);
        return ResponseEntity.ok(publicaciones);
    }

    // HU21 - Agregar comentario
    @PostMapping("/{publicacionId}/comentarios")
    public ResponseEntity<Comentario> agregarComentario(
        @PathVariable Long publicacionId,
        @RequestBody Map<String, Object> request) {
        Long autorId = Long.valueOf(request.get("autorId").toString());
        String contenido = request.get("contenido").toString();

        Comentario comentario = publicacionService.agregarComentario(publicacionId, autorId, contenido);
        return ResponseEntity.ok(comentario);
    }

    // HU22 - Dar reacción
    @PostMapping("/{publicacionId}/reacciones")
    public ResponseEntity<Reaccion> darReaccion(
        @PathVariable Long publicacionId,
        @RequestBody Map<String, Object> request) {
        Long usuarioId = Long.valueOf(request.get("usuarioId").toString());
        String tipo = request.getOrDefault("tipo", "ME_GUSTA").toString();

        Reaccion reaccion = publicacionService.darReaccion(publicacionId, usuarioId, tipo);
        return ResponseEntity.ok(reaccion);
    }

    // Quitar reacción
    @DeleteMapping("/{publicacionId}/reacciones/{usuarioId}")
    public ResponseEntity<Void> quitarReaccion(
        @PathVariable Long publicacionId,
        @PathVariable Long usuarioId) {
        publicacionService.quitarReaccion(publicacionId, usuarioId);
        return ResponseEntity.ok().build();
    }
}
