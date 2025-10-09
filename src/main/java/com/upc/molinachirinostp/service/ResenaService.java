package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.ResenaMentor;
import com.upc.molinachirinostp.entity.SesionMentoria;
import com.upc.molinachirinostp.repository.ResenaMentorRepository;
import com.upc.molinachirinostp.repository.SesionMentoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ResenaService {
    private final ResenaMentorRepository resenaRepo;
    private final SesionMentoriaRepository sesionRepo;

    public ResenaMentor crear(Long idSesion, Integer calificacion, String comentario, LocalDate fecha){
        SesionMentoria sesion = sesionRepo.findById(idSesion)
                .orElseThrow(() -> new IllegalArgumentException("sesion not found"));

        // 1 reseña por sesión
        resenaRepo.findBySesionIdSesion(idSesion).ifPresent(r -> {
            throw new IllegalArgumentException("ya existe reseña para esta sesión");
        });

        if (calificacion == null || calificacion < 1 || calificacion > 5)
            throw new IllegalArgumentException("calificacion debe ser 1..5");

        ResenaMentor r = new ResenaMentor(null, sesion, calificacion, comentario, fecha);
        return resenaRepo.save(r);
    }
}
