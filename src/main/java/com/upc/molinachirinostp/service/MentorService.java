package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.*;
import com.upc.molinachirinostp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepo;
    private final UsuarioRepository usuarioRepo;
    private final HabilidadRepository habilidadRepo;
    private final MentorHabilidadRepository mentorHabRepo;

    public Mentor crear(Long idUser, String bio){
        Usuario u = usuarioRepo.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("usuario not found"));
        Mentor m = new Mentor();
        m.setUsuario(u);
        m.setBio(bio);
        return mentorRepo.save(m);
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
}
