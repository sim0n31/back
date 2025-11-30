package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.entity.Mensaje;
import com.upc.molinachirinostp.service.MensajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    // HU19 - Enviar mensaje
    @PostMapping("/enviar")
    public ResponseEntity<Mensaje> enviarMensaje(@RequestBody Map<String, Object> request) {
        Long emisorId = Long.valueOf(request.get("emisorId").toString());
        Long receptorId = Long.valueOf(request.get("receptorId").toString());
        String contenido = request.get("contenido").toString();

        Mensaje mensaje = mensajeService.enviarMensaje(emisorId, receptorId, contenido);
        return ResponseEntity.ok(mensaje);
    }

    // HU19 - Ver conversación
    @GetMapping("/conversacion")
    public ResponseEntity<List<Mensaje>> getConversacion(
        @RequestParam Long usuario1Id,
        @RequestParam Long usuario2Id) {
        List<Mensaje> mensajes = mensajeService.getConversacion(usuario1Id, usuario2Id);
        return ResponseEntity.ok(mensajes);
    }

    // Marcar mensaje como leído
    @PutMapping("/{id}/leer")
    public ResponseEntity<Void> marcarComoLeido(@PathVariable Long id) {
        mensajeService.marcarComoLeido(id);
        return ResponseEntity.ok().build();
    }

    // Obtener mensajes no leídos
    @GetMapping("/no-leidos/{usuarioId}")
    public ResponseEntity<List<Mensaje>> getMensajesNoLeidos(@PathVariable Long usuarioId) {
        List<Mensaje> mensajes = mensajeService.getMensajesNoLeidos(usuarioId);
        return ResponseEntity.ok(mensajes);
    }
}
