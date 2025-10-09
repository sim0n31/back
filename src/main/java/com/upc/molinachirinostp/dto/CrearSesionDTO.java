package com.upc.molinachirinostp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Request para crear una sesión de mentoría */
public record CrearSesionDTO(
        @NotNull Long idAlumno,
        @NotNull Long idMentor,
        @NotNull String fechaInicio,   // "2025-10-03" (ISO)
        @NotBlank String canal,        // "ZOOM", "PRESENCIAL", etc.
        @NotBlank String estado,       // "PENDIENTE", "CONFIRMADA", ...
        String notas                   // opcional
) {}
