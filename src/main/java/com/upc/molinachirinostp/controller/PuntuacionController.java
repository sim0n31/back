package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Puntuacion;
import com.upc.molinachirinostp.service.PuntuacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puntuaciones")
@PreAuthorize("hasAnyRole('USER', 'MENTOR', 'ADMIN')")
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
}
