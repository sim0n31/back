package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.dto.CrearSesionDTO;
import com.upc.molinachirinostp.dto.SesionViewDTO;
import com.upc.molinachirinostp.service.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/connected")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;

    // Crear sesión (usa DTO de entrada y devuelve DTO de salida)
    @PostMapping("/sesiones")
    public SesionViewDTO crear(@RequestBody @Valid CrearSesionDTO dto) {
        return sesionService.crear(dto);
    }

    // Listar sesiones por alumno y fecha (devuelve lista de DTOs)
    @GetMapping("/sesiones")
    public List<SesionViewDTO> porAlumnoYFecha(
            @RequestParam Long idAlumno,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return sesionService.porAlumnoYFecha(idAlumno, fecha);
    }
}
