package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.*;
import com.upc.molinachirinostp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final ComentarioRepository comentarioRepository;
    private final ReaccionRepository reaccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PuntuacionService puntuacionService;

    public PublicacionService(PublicacionRepository publicacionRepository,
                            ComentarioRepository comentarioRepository,
                            ReaccionRepository reaccionRepository,
                            UsuarioRepository usuarioRepository,
                            PuntuacionService puntuacionService) {
        this.publicacionRepository = publicacionRepository;
        this.comentarioRepository = comentarioRepository;
        this.reaccionRepository = reaccionRepository;
        this.usuarioRepository = usuarioRepository;
        this.puntuacionService = puntuacionService;
    }

    // HU20 - Crear publicación
    @Transactional
    public Publicacion crearPublicacion(Long autorId, String contenido, String imagen) {
        Usuario autor = usuarioRepository.findById(autorId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Publicacion publicacion = new Publicacion();
        publicacion.setAutor(autor);
        publicacion.setContenido(contenido);
        publicacion.setImagen(imagen);
        publicacion.setFechaPublicacion(LocalDateTime.now());

        Publicacion saved = publicacionRepository.save(publicacion);

        // HU25 - Sumar puntos por publicación
        puntuacionService.agregarPuntosPublicacion(autorId, 10);

        return saved;
    }

    // Ver feed completo
    public List<Publicacion> getFeed() {
        return publicacionRepository.findAllByOrderByFechaPublicacionDesc();
    }

    // Ver publicaciones de un usuario
    public List<Publicacion> getPublicacionesByUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return publicacionRepository.findByAutorOrderByFechaPublicacionDesc(usuario);
    }

    // HU21 - Agregar comentario
    @Transactional
    public Comentario agregarComentario(Long publicacionId, Long autorId, String contenido) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
            .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        Usuario autor = usuarioRepository.findById(autorId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comentario comentario = new Comentario();
        comentario.setPublicacion(publicacion);
        comentario.setAutor(autor);
        comentario.setContenido(contenido);
        comentario.setFechaComentario(LocalDateTime.now());

        Comentario saved = comentarioRepository.save(comentario);

        // HU25 - Sumar puntos por comentario
        puntuacionService.agregarPuntosComentario(autorId, 5);

        return saved;
    }

    // HU22 - Dar reacción (me gusta)
    @Transactional
    public Reaccion darReaccion(Long publicacionId, Long usuarioId, String tipo) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
            .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si ya existe reacción
        return reaccionRepository.findByPublicacionAndUsuario(publicacion, usuario)
            .map(r -> {
                r.setTipo(tipo);
                return reaccionRepository.save(r);
            })
            .orElseGet(() -> {
                Reaccion nuevaReaccion = new Reaccion();
                nuevaReaccion.setPublicacion(publicacion);
                nuevaReaccion.setUsuario(usuario);
                nuevaReaccion.setTipo(tipo);
                return reaccionRepository.save(nuevaReaccion);
            });
    }

    // Quitar reacción
    @Transactional
    public void quitarReaccion(Long publicacionId, Long usuarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
            .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        reaccionRepository.findByPublicacionAndUsuario(publicacion, usuario)
            .ifPresent(reaccionRepository::delete);
    }
}
