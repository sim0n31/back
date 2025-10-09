package com.upc.molinachirinostp.dto;

/** Respuesta para reporte: total de sesiones por mentor en un rango */
public record ConteoSesionesPorMentorDTO(
        Long idMentor,
        String mentor,
        Long totalSesiones
) {}
