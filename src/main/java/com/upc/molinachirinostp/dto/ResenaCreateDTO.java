package com.upc.molinachirinostp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Request para registrar reseña de una sesión (1 reseña por sesión) */
public record ResenaCreateDTO(
        @NotNull Long idSesion,
        @NotNull @Min(1) @Max(5) Integer calificacion,
        String comentario,
        @NotNull String fecha          // "2025-10-03" (ISO)
) {}
