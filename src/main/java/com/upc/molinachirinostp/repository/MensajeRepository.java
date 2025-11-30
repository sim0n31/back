package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Mensaje;
import com.upc.molinachirinostp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    @Query("SELECT m FROM Mensaje m WHERE (m.emisor = ?1 AND m.receptor = ?2) OR (m.emisor = ?2 AND m.receptor = ?1) ORDER BY m.fechaEnvio")
    List<Mensaje> findConversacion(Usuario usuario1, Usuario usuario2);

    List<Mensaje> findByReceptorAndLeidoFalse(Usuario receptor);
}
