package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Oportunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OportunidadRepository extends JpaRepository<Oportunidad, Long> {
    List<Oportunidad> findByTipoAndActivoTrue(String tipo);
    List<Oportunidad> findByActivoTrueOrderByFechaInicioDesc();
}
