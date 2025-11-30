package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Conexion;
import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.service.ConexionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conexiones")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
public class ConexionController {

    private final ConexionService conexionService;

    public ConexionController(ConexionService conexionService) {
        this.conexionService = conexionService;
    }

    // HU05 - Enviar solicitud de conexión
    @PostMapping("/enviar")
    public ResponseEntity<Conexion> enviarSolicitud(@RequestBody Map<String, Long> request) {
        Long solicitanteId = request.get("solicitanteId");
        Long receptorId = request.get("receptorId");
        Conexion conexion = conexionService.enviarSolicitud(solicitanteId, receptorId);
        return ResponseEntity.ok(conexion);
    }

    // HU06 - Aceptar solicitud
    @PutMapping("/{id}/aceptar")
    public ResponseEntity<Conexion> aceptarSolicitud(@PathVariable Long id) {
        Conexion conexion = conexionService.aceptarSolicitud(id);
        return ResponseEntity.ok(conexion);
    }

    // HU06 - Rechazar solicitud
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Conexion> rechazarSolicitud(@PathVariable Long id) {
        Conexion conexion = conexionService.rechazarSolicitud(id);
        return ResponseEntity.ok(conexion);
    }

    // Ver solicitudes pendientes
    @GetMapping("/pendientes/{usuarioId}")
    public ResponseEntity<List<Conexion>> getSolicitudesPendientes(@PathVariable Long usuarioId) {
        List<Conexion> solicitudes = conexionService.getSolicitudesPendientes(usuarioId);
        return ResponseEntity.ok(solicitudes);
    }

    // Ver solicitudes recibidas (todas)
    @GetMapping("/recibidas/{usuarioId}")
    public ResponseEntity<List<Conexion>> getSolicitudesRecibidas(@PathVariable Long usuarioId) {
        List<Conexion> solicitudes = conexionService.getSolicitudesRecibidas(usuarioId);
        return ResponseEntity.ok(solicitudes);
    }

    // Ver solicitudes enviadas
    @GetMapping("/enviadas/{usuarioId}")
    public ResponseEntity<List<Conexion>> getSolicitudesEnviadas(@PathVariable Long usuarioId) {
        List<Conexion> solicitudes = conexionService.getSolicitudesEnviadas(usuarioId);
        return ResponseEntity.ok(solicitudes);
    }

    // HU07 - Ver lista de contactos
    @GetMapping("/contactos/{usuarioId}")
    public ResponseEntity<List<Usuario>> getContactos(@PathVariable Long usuarioId) {
        List<Usuario> contactos = conexionService.getContactos(usuarioId);
        return ResponseEntity.ok(contactos);
    }

    // HU09 - Sugerencias de conexión
    @GetMapping("/sugerencias/{usuarioId}")
    public ResponseEntity<List<Usuario>> getSugerencias(@PathVariable Long usuarioId, @RequestParam(defaultValue = "10") int limit) {
        List<Usuario> sugerencias = conexionService.getSugerencias(usuarioId, limit);
        return ResponseEntity.ok(sugerencias);
    }
}
