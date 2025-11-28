package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Publicacion;
import com.upc.molinachirinostp.entity.Reaccion;
import com.upc.molinachirinostp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaccionRepository extends JpaRepository<Reaccion, Long> {
    Optional<Reaccion> findByPublicacionAndUsuario(Publicacion publicacion, Usuario usuario);
    Long countByPublicacion(Publicacion publicacion);
}
