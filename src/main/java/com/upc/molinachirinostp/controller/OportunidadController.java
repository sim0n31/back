package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Oportunidad;
import com.upc.molinachirinostp.service.OportunidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oportunidades")
public class OportunidadController {

    private final OportunidadService oportunidadService;

    public OportunidadController(OportunidadService oportunidadService) {
        this.oportunidadService = oportunidadService;
    }

    // HU15 - Recomendación de empleos
    @GetMapping("/empleos")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Oportunidad>> getEmpleos() {
        List<Oportunidad> empleos = oportunidadService.getEmpleos();
        return ResponseEntity.ok(empleos);
    }

    // HU16 - Recomendación de pasantías
    @GetMapping("/pasantias")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Oportunidad>> getPasantias() {
        List<Oportunidad> pasantias = oportunidadService.getPasantias();
        return ResponseEntity.ok(pasantias);
    }

    // HU17 - Recomendación de talleres
    @GetMapping("/talleres")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Oportunidad>> getTalleres() {
        List<Oportunidad> talleres = oportunidadService.getTalleres();
        return ResponseEntity.ok(talleres);
    }

    // HU18 - Eventos
    @GetMapping("/eventos")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Oportunidad>> getEventos() {
        List<Oportunidad> eventos = oportunidadService.getEventos();
        return ResponseEntity.ok(eventos);
    }

    // Obtener todas las oportunidades
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Oportunidad>> getAllOportunidades() {
        List<Oportunidad> oportunidades = oportunidadService.getAllOportunidades();
        return ResponseEntity.ok(oportunidades);
    }

    // Crear oportunidad (Todos los usuarios autenticados pueden crear)
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
    public ResponseEntity<Oportunidad> crearOportunidad(@RequestBody Oportunidad oportunidad) {
        Oportunidad nueva = oportunidadService.crearOportunidad(oportunidad);
        return ResponseEntity.ok(nueva);
    }

    // Actualizar oportunidad (Solo ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Oportunidad> actualizarOportunidad(@PathVariable Long id, @RequestBody Oportunidad oportunidad) {
        Oportunidad actualizada = oportunidadService.actualizarOportunidad(id, oportunidad);
        return ResponseEntity.ok(actualizada);
    }

    // Desactivar oportunidad (Solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> desactivarOportunidad(@PathVariable Long id) {
        oportunidadService.desactivarOportunidad(id);
        return ResponseEntity.ok().build();
    }
}
