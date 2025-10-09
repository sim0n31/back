package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.Usuario;
import com.upc.molinachirinostp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository userRepo;

    public Usuario crear(String primerNombre, String primerApellido, String email,
                         String pais, String ciudad, String password,
                         LocalDate fechaNacimiento, String descripcion) {
        Usuario u = new Usuario();
        u.setPrimerNombre(primerNombre);
        u.setPrimerApellido(primerApellido);
        u.setEmail(email);
        u.setPais(pais);
        u.setCiudad(ciudad);
        u.setPassword(password);
        u.setFechaNacimiento(fechaNacimiento);
        u.setDescripcion(descripcion);
        return userRepo.save(u);
    }

    public Usuario get(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuario not found"));
    }

    public List<Usuario> listar() {
        return userRepo.findAll();
    }
}
