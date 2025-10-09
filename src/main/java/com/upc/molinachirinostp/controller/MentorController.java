package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.dto.AgregarHabilidadDTO;
import com.upc.molinachirinostp.entity.MentorHabilidad;
import com.upc.molinachirinostp.service.MentorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/connected/mentores")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    // Agregar una habilidad a un mentor
    @PostMapping("/{idMentor}/habilidades")
    public MentorHabilidad agregarHabilidad(@PathVariable Long idMentor,
                                            @RequestBody @Valid AgregarHabilidadDTO dto) {
        return mentorService.agregarHabilidad(idMentor, dto.idHabilidad(), LocalDate.parse(dto.desde()));
    }
}
