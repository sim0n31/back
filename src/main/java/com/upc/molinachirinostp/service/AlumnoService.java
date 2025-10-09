package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.Alumno;
import com.upc.molinachirinostp.entity.Universidad;
import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.AlumnoRepository;
import com.upc.molinachirinostp.repository.UniversidadRepository;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository alumnoRepo;
    private final UsuarioRepository usuarioRepo;
    private final UniversidadRepository universidadRepo;

    public Alumno crear(Long idUser, Long idUniversidad, String semestre, String estatusEstudio){
        Usuario usuario = usuarioRepo.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("usuario not found"));
        Universidad uni = universidadRepo.findById(idUniversidad)
                .orElseThrow(() -> new IllegalArgumentException("universidad not found"));

        Alumno a = new Alumno();
        a.setUsuario(usuario);
        a.setUniversidad(uni);
        a.setSemestre(semestre);
        a.setEstatusEstudio(estatusEstudio);
        return alumnoRepo.save(a);
    }

    public Alumno get(Long idAlumno){
        return alumnoRepo.findById(idAlumno)
                .orElseThrow(() -> new IllegalArgumentException("alumno not found"));
    }
}
