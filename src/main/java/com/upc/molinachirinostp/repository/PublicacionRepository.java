package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Publicacion;
import com.upc.molinachirinostp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByAutorOrderByFechaPublicacionDesc(Usuario autor);
    List<Publicacion> findAllByOrderByFechaPublicacionDesc();
}
