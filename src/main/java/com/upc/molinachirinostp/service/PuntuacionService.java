package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.Puntuacion;
import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.PuntuacionRepository;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuntuacionService {

    private final PuntuacionRepository puntuacionRepository;
    private final UsuarioRepository usuarioRepository;

    public PuntuacionService(PuntuacionRepository puntuacionRepository, UsuarioRepository usuarioRepository) {
        this.puntuacionRepository = puntuacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // HU25 - Obtener puntuación de un usuario
    public Puntuacion getPuntuacion(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return puntuacionRepository.findByUsuario(usuario)
            .orElseGet(() -> {
                Puntuacion nueva = new Puntuacion();
                nueva.setUsuario(usuario);
                return puntuacionRepository.save(nueva);
            });
    }

    // Agregar puntos por publicación
    @Transactional
    public void agregarPuntosPublicacion(Long usuarioId, Integer puntos) {
        Puntuacion puntuacion = getPuntuacion(usuarioId);
        puntuacion.setPuntosPublicaciones(puntuacion.getPuntosPublicaciones() + puntos);
        puntuacionRepository.save(puntuacion);
    }

    // Agregar puntos por conexión
    @Transactional
    public void agregarPuntosConexion(Long usuarioId, Integer puntos) {
        Puntuacion puntuacion = getPuntuacion(usuarioId);
        puntuacion.setPuntosConexiones(puntuacion.getPuntosConexiones() + puntos);
        puntuacionRepository.save(puntuacion);
    }

    // Agregar puntos por mentoría
    @Transactional
    public void agregarPuntosMentoria(Long usuarioId, Integer puntos) {
        Puntuacion puntuacion = getPuntuacion(usuarioId);
        puntuacion.setPuntosMentorias(puntuacion.getPuntosMentorias() + puntos);
        puntuacionRepository.save(puntuacion);
    }

    // Agregar puntos por comentario
    @Transactional
    public void agregarPuntosComentario(Long usuarioId, Integer puntos) {
        Puntuacion puntuacion = getPuntuacion(usuarioId);
        puntuacion.setPuntosComentarios(puntuacion.getPuntosComentarios() + puntos);
        puntuacionRepository.save(puntuacion);
    }
}
