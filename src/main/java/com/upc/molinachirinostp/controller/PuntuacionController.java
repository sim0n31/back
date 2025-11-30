package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Puntuacion;
import com.upc.molinachirinostp.service.PuntuacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puntuaciones")
<<<<<<< HEAD
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
=======
@PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
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
<<<<<<< HEAD

    // Obtener puntos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<java.util.List<Puntuacion>> getPuntosPorUsuario(@PathVariable Long usuarioId) {
        java.util.List<Puntuacion> puntuaciones = puntuacionService.getPuntosPorUsuario(usuarioId);
        return ResponseEntity.ok(puntuaciones);
    }
=======
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
}
