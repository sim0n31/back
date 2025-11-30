package com.upc.molinachirinostp.controller;

import com.upc.molinachirinostp.dto.AgregarHabilidadDTO;
import com.upc.molinachirinostp.entity.Mentor;
import com.upc.molinachirinostp.entity.MentorHabilidad;
import com.upc.molinachirinostp.service.MentorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/connected/mentores")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MENTOR', 'ROLE_ADMIN')")
public class MentorController {

    private final MentorService mentorService;

    // Registrarse como mentor (ruta alternativa para frontend)
    @PostMapping
    public Mentor registrarseMentor(@RequestBody Mentor mentor) {
        return mentorService.crearMentor(mentor);
    }

    // Registrarse como mentor (ruta con /registro)
    @PostMapping("/registro")
    public Mentor registrarseMentorRegistro(@RequestBody Mentor mentor) {
        return mentorService.crearMentor(mentor);
    }

    // Obtener todos los mentores
    @GetMapping
    public List<Mentor> obtenerTodos() {
        return mentorService.obtenerTodos();
    }

    // Obtener un mentor por ID
    @GetMapping("/{idMentor}")
    public Mentor obtenerPorId(@PathVariable Long idMentor) {
        return mentorService.obtenerPorId(idMentor);
    }

    // Agregar una habilidad a un mentor
    @PostMapping("/{idMentor}/habilidades")
    public MentorHabilidad agregarHabilidad(@PathVariable Long idMentor,
                                            @RequestBody @Valid AgregarHabilidadDTO dto) {
        return mentorService.agregarHabilidad(idMentor, dto.idHabilidad(), LocalDate.parse(dto.desde()));
    }

    // Obtener mentor por ID de usuario
    @GetMapping("/usuario/{idUsuario}")
    public Mentor obtenerPorIdUsuario(@PathVariable Long idUsuario) {
        return mentorService.obtenerPorIdUsuario(idUsuario);
    }

    // Verificar si un usuario es mentor
    @GetMapping("/verificar/{idUsuario}")
    public boolean esUsuarioMentor(@PathVariable Long idUsuario) {
        return mentorService.esUsuarioMentor(idUsuario);
    }
}
