package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.dto.ResenaCreateDTO;
import com.upc.molinachirinostp.entity.ResenaMentor;
import com.upc.molinachirinostp.service.ResenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/connected/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    // Crear reseña para una sesión (1 reseña por sesión)
    @PostMapping
    public ResenaMentor crear(@RequestBody @Valid ResenaCreateDTO dto) {
        return resenaService.crear(
                dto.idSesion(),
                dto.calificacion(),
                dto.comentario(),
                LocalDate.parse(dto.fecha())
        );
    }
}
