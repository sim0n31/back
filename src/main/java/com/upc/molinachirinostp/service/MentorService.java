package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.*;
import com.upc.molinachirinostp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepo;
    private final UsuarioRepository usuarioRepo;
    private final HabilidadRepository habilidadRepo;
    private final MentorHabilidadRepository mentorHabRepo;

    public List<Mentor> obtenerTodos() {
        return mentorRepo.findAll();
    }

    public Mentor obtenerPorId(Long idMentor) {
        return mentorRepo.findById(idMentor)
                .orElseThrow(() -> new IllegalArgumentException("mentor not found"));
    }

    public Mentor crear(Long idUser, String bio){
        Usuario u = usuarioRepo.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("usuario not found"));
        Mentor m = new Mentor();
        m.setUsuario(u);
        m.setBio(bio);
        return mentorRepo.save(m);
    }

    // Crear mentor desde objeto Mentor (para formulario de registro)
    public Mentor crearMentor(Mentor mentor) {
        if (mentor.getUsuario() != null && mentor.getUsuario().getIdUser() != null) {
            Usuario u = usuarioRepo.findById(mentor.getUsuario().getIdUser())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            mentor.setUsuario(u);
        }
        return mentorRepo.save(mentor);
    }

    @Transactional
    public MentorHabilidad agregarHabilidad(Long idMentor, Long idHabilidad, LocalDate desde){
        Mentor mentor = mentorRepo.findById(idMentor)
                .orElseThrow(() -> new IllegalArgumentException("mentor not found"));
        Habilidad hab = habilidadRepo.findById(idHabilidad)
                .orElseThrow(() -> new IllegalArgumentException("habilidad not found"));
        MentorHabilidad mh = new MentorHabilidad(null, mentor, hab, desde);
        return mentorHabRepo.save(mh);
    }

    // Obtener mentor por ID de usuario
    public Mentor obtenerPorIdUsuario(Long idUsuario) {
        return mentorRepo.findByUsuario_IdUser(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Mentor no encontrado para este usuario"));
    }

    // Verificar si un usuario es mentor
    public boolean esUsuarioMentor(Long idUsuario) {
        return mentorRepo.findByUsuario_IdUser(idUsuario).isPresent();
    }
}
