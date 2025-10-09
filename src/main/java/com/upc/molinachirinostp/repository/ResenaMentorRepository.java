package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.ResenaMentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResenaMentorRepository extends JpaRepository<ResenaMentor, Long> {
    // Útil para validar “una reseña por sesión”
    Optional<ResenaMentor> findBySesionIdSesion(Long idSesion);
}
