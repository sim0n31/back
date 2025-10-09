package com.upc.molinachirinostp.dto;

public record SesionViewDTO(
        Long idSesion,
        String fechaInicio,     // "YYYY-MM-DD"
        String canal,
        String estado,
        String notas,
        Long idAlumno,
        String alumnoNombre,
        Long idMentor,
        String mentorNombre
) {}
