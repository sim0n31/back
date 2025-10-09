package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.dto.CrearSesionDTO;
import com.upc.molinachirinostp.dto.SesionViewDTO;
import com.upc.molinachirinostp.entity.Alumno;
import com.upc.molinachirinostp.entity.Mentor;
import com.upc.molinachirinostp.entity.SesionMentoria;
import com.upc.molinachirinostp.repository.AlumnoRepository;
import com.upc.molinachirinostp.repository.MentorRepository;
import com.upc.molinachirinostp.repository.SesionMentoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SesionService {
    private final SesionMentoriaRepository sesionRepo;
    private final AlumnoRepository alumnoRepo;
    private final MentorRepository mentorRepo;

    // ==========
    // MAPEADORES
    // ==========
    private SesionViewDTO toView(SesionMentoria s) {
        String alumnoNombre = null;
        if (s.getAlumno() != null && s.getAlumno().getUsuario() != null) {
            var u = s.getAlumno().getUsuario();
            alumnoNombre = (u.getPrimerNombre() != null ? u.getPrimerNombre() : "")
                    + " "
                    + (u.getPrimerApellido() != null ? u.getPrimerApellido() : "");
            alumnoNombre = alumnoNombre.trim();
        }

        String mentorNombre = null;
        if (s.getMentor() != null && s.getMentor().getUsuario() != null) {
            var u = s.getMentor().getUsuario();
            mentorNombre = (u.getPrimerNombre() != null ? u.getPrimerNombre() : "")
                    + " "
                    + (u.getPrimerApellido() != null ? u.getPrimerApellido() : "");
            mentorNombre = mentorNombre.trim();
        }

        return new SesionViewDTO(
                s.getIdSesion(),
                s.getFechaInicio() != null ? s.getFechaInicio().toString() : null,
                s.getCanal(),
                s.getEstado(),
                s.getNotas(),
                s.getAlumno() != null ? s.getAlumno().getIdAlumno() : null,
                alumnoNombre,
                s.getMentor() != null ? s.getMentor().getIdMentor() : null,
                mentorNombre
        );
    }

    // ==========================
    // MÉTODOS VERSION "ENTITIES"
    // (compatibles con tu código)
    // ==========================
    public SesionMentoria crear(Long idAlumno, Long idMentor, LocalDate fechaInicio,
                                String canal, String estado, String notas) {
        Alumno a = alumnoRepo.findById(idAlumno)
                .orElseThrow(() -> new IllegalArgumentException("alumno not found"));
        Mentor m = mentorRepo.findById(idMentor)
                .orElseThrow(() -> new IllegalArgumentException("mentor not found"));

        if (fechaInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("fecha debe ser hoy o futura");
        }

        SesionMentoria s = new SesionMentoria(null, fechaInicio, canal, estado, notas, a, m);
        return sesionRepo.save(s);
    }

    public List<SesionMentoria> listarPorAlumnoYFecha(Long idAlumno, LocalDate fecha) {
        return sesionRepo.findByAlumnoIdAlumnoAndFechaInicio(idAlumno, fecha);
    }

    // ==============================
    // MÉTODOS VERSION "CON DTOs"
    // (recomendados para Controllers)
    // ==============================
    public SesionViewDTO crear(CrearSesionDTO dto) {
        // Parseo y validaciones básicas
        LocalDate f = LocalDate.parse(dto.fechaInicio());
        if (f.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("fecha debe ser hoy o futura");
        }
        if (dto.canal() == null || dto.canal().isBlank()) {
            throw new IllegalArgumentException("canal es obligatorio");
        }
        if (dto.estado() == null || dto.estado().isBlank()) {
            throw new IllegalArgumentException("estado es obligatorio");
        }

        // Buscar relaciones
        Alumno a = alumnoRepo.findById(dto.idAlumno())
                .orElseThrow(() -> new IllegalArgumentException("alumno not found"));
        Mentor m = mentorRepo.findById(dto.idMentor())
                .orElseThrow(() -> new IllegalArgumentException("mentor not found"));

        // Guardar
        SesionMentoria s = new SesionMentoria(null, f, dto.canal(), dto.estado(), dto.notas(), a, m);
        SesionMentoria saved = sesionRepo.save(s);

        // Mapear a DTO de salida
        return toView(saved);
    }

    public List<SesionViewDTO> porAlumnoYFecha(Long idAlumno, LocalDate fecha) {
        return sesionRepo.findByAlumnoIdAlumnoAndFechaInicio(idAlumno, fecha)
                .stream()
                .map(this::toView)
                .toList();
    }
}
