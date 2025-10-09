package com.upc.molinachirinostp.dto;

import jakarta.validation.constraints.NotNull;

/** Request para asociar una habilidad a un mentor */
public record AgregarHabilidadDTO(
        @NotNull Long idHabilidad,
        @NotNull String desde           // "2025-10-01" (ISO)
) {}
