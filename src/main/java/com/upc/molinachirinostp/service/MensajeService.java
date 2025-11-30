package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.Mensaje;
import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.MensajeRepository;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final UsuarioRepository usuarioRepository;

    public MensajeService(MensajeRepository mensajeRepository, UsuarioRepository usuarioRepository) {
        this.mensajeRepository = mensajeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // HU19 - Enviar mensaje
    @Transactional
    public Mensaje enviarMensaje(Long emisorId, Long receptorId, String contenido) {
        Usuario emisor = usuarioRepository.findById(emisorId)
            .orElseThrow(() -> new RuntimeException("Emisor no encontrado"));
        Usuario receptor = usuarioRepository.findById(receptorId)
            .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));

        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor(emisor);
        mensaje.setReceptor(receptor);
        mensaje.setContenido(contenido);
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setLeido(false);

        return mensajeRepository.save(mensaje);
    }

    // HU19 - Ver conversación
    public List<Mensaje> getConversacion(Long usuario1Id, Long usuario2Id) {
        Usuario usuario1 = usuarioRepository.findById(usuario1Id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Usuario usuario2 = usuarioRepository.findById(usuario2Id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mensajeRepository.findConversacion(usuario1, usuario2);
    }

    // Marcar mensajes como leídos
    @Transactional
    public void marcarComoLeido(Long mensajeId) {
        Mensaje mensaje = mensajeRepository.findById(mensajeId)
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        mensaje.setLeido(true);
        mensajeRepository.save(mensaje);
    }

    // Obtener mensajes no leídos
    public List<Mensaje> getMensajesNoLeidos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mensajeRepository.findByReceptorAndLeidoFalse(usuario);
    }
}
