package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Comentario;
import com.upc.molinachirinostp.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacionOrderByFechaComentario(Publicacion publicacion);
}
