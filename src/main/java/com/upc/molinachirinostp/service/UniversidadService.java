package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.Universidad;
import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.UniversidadRepository;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversidadService {
    private final UniversidadRepository universidadRepo;
    private final UsuarioRepository usuarioRepo;

    public Universidad crear(String nombre, String pais, String ciudad, String siglas, Long idCreador) {
        Universidad u = new Universidad();
        u.setNombre(nombre);
        u.setPais(pais);
        u.setCiudad(ciudad);
        u.setSiglas(siglas);
        if (idCreador != null) {
            Usuario creador = usuarioRepo.findById(idCreador)
                    .orElseThrow(() -> new IllegalArgumentException("creador not found"));
            u.setCreador(creador);
        }
        return universidadRepo.save(u);
    }

    public List<Universidad> listar() { return universidadRepo.findAll(); }
}
