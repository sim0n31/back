package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.Conexion;
import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.ConexionRepository;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConexionService {

    private final ConexionRepository conexionRepository;
    private final UsuarioRepository usuarioRepository;

    public ConexionService(ConexionRepository conexionRepository, UsuarioRepository usuarioRepository) {
        this.conexionRepository = conexionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // HU05 - Enviar solicitud de conexión
    @Transactional
    public Conexion enviarSolicitud(Long solicitanteId, Long receptorId) {
        Usuario solicitante = usuarioRepository.findById(solicitanteId)
            .orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));
        Usuario receptor = usuarioRepository.findById(receptorId)
            .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));

        // Verificar que no exista ya una conexión
        if (conexionRepository.findBySolicitanteAndReceptor(solicitante, receptor).isPresent() ||
            conexionRepository.findBySolicitanteAndReceptor(receptor, solicitante).isPresent()) {
            throw new RuntimeException("Ya existe una solicitud de conexión");
        }

        Conexion conexion = new Conexion();
        conexion.setSolicitante(solicitante);
        conexion.setReceptor(receptor);
        conexion.setEstado("PENDIENTE");
        conexion.setFechaSolicitud(LocalDateTime.now());

        return conexionRepository.save(conexion);
    }

    // HU06 - Aceptar solicitud
    @Transactional
    public Conexion aceptarSolicitud(Long conexionId) {
        Conexion conexion = conexionRepository.findById(conexionId)
            .orElseThrow(() -> new RuntimeException("Conexión no encontrada"));

        conexion.setEstado("ACEPTADA");
        conexion.setFechaRespuesta(LocalDateTime.now());

        return conexionRepository.save(conexion);
    }

    // HU06 - Rechazar solicitud
    @Transactional
    public Conexion rechazarSolicitud(Long conexionId) {
        Conexion conexion = conexionRepository.findById(conexionId)
            .orElseThrow(() -> new RuntimeException("Conexión no encontrada"));

        conexion.setEstado("RECHAZADA");
        conexion.setFechaRespuesta(LocalDateTime.now());

        return conexionRepository.save(conexion);
    }

    // Ver solicitudes pendientes recibidas
    public List<Conexion> getSolicitudesPendientes(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return conexionRepository.findByReceptorAndEstado(usuario, "PENDIENTE");
    }

    // Ver solicitudes recibidas (todas)
    public List<Conexion> getSolicitudesRecibidas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return conexionRepository.findByReceptor(usuario);
    }

    // Ver solicitudes enviadas
    public List<Conexion> getSolicitudesEnviadas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return conexionRepository.findBySolicitante(usuario);
    }

    // HU07 - Ver lista de contactos
    public List<Usuario> getContactos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Conexion> conexiones = conexionRepository.findContactosByUsuario(usuario);

        return conexiones.stream()
            .map(c -> c.getSolicitante().getIdUser().equals(usuarioId) ? c.getReceptor() : c.getSolicitante())
            .collect(Collectors.toList());
    }

    // HU09 - Sugerencias de conexión (simplificado: usuarios que no son contactos)
    public List<Usuario> getSugerencias(Long usuarioId, int limit) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Usuario> contactos = getContactos(usuarioId);
        List<Long> contactosIds = contactos.stream().map(Usuario::getIdUser).collect(Collectors.toList());
        contactosIds.add(usuarioId); // Excluir al propio usuario

        return usuarioRepository.findAll().stream()
            .filter(u -> !contactosIds.contains(u.getIdUser()))
            .limit(limit)
            .collect(Collectors.toList());
    }
}
