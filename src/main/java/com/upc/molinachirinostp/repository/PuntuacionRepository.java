package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Puntuacion;
import com.upc.molinachirinostp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {
    Optional<Puntuacion> findByUsuario(Usuario usuario);
}
