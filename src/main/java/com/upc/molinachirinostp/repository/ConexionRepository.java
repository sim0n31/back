package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Conexion;
import com.upc.molinachirinostp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConexionRepository extends JpaRepository<Conexion, Long> {
    List<Conexion> findByReceptorAndEstado(Usuario receptor, String estado);
    List<Conexion> findBySolicitanteAndEstado(Usuario solicitante, String estado);

    @Query("SELECT c FROM Conexion c WHERE (c.solicitante = ?1 OR c.receptor = ?1) AND c.estado = 'ACEPTADA'")
    List<Conexion> findContactosByUsuario(Usuario usuario);

    Optional<Conexion> findBySolicitanteAndReceptor(Usuario solicitante, Usuario receptor);
}
