package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Puntuacion;
import com.upc.molinachirinostp.service.PuntuacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puntuaciones")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
public class PuntuacionController {

    private final PuntuacionService puntuacionService;

    public PuntuacionController(PuntuacionService puntuacionService) {
        this.puntuacionService = puntuacionService;
    }

    // HU25 - Obtener puntuación de un usuario
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Puntuacion> getPuntuacion(@PathVariable Long usuarioId) {
        Puntuacion puntuacion = puntuacionService.getPuntuacion(usuarioId);
        return ResponseEntity.ok(puntuacion);
    }

    // Obtener puntos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<java.util.List<Puntuacion>> getPuntosPorUsuario(@PathVariable Long usuarioId) {
        java.util.List<Puntuacion> puntuaciones = puntuacionService.getPuntosPorUsuario(usuarioId);
        return ResponseEntity.ok(puntuaciones);
    }
}
