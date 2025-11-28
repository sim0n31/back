package com.upc.molinachirinostp.service;

import com.upc.molinachirinostp.entity.Oportunidad;
import com.upc.molinachirinostp.repository.OportunidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OportunidadService {

    private final OportunidadRepository oportunidadRepository;

    public OportunidadService(OportunidadRepository oportunidadRepository) {
        this.oportunidadRepository = oportunidadRepository;
    }

    // HU15 - Recomendación de empleos
    public List<Oportunidad> getEmpleos() {
        return oportunidadRepository.findByTipoAndActivoTrue("EMPLEO");
    }

    // HU16 - Recomendación de pasantías
    public List<Oportunidad> getPasantias() {
        return oportunidadRepository.findByTipoAndActivoTrue("PASANTIA");
    }

    // HU17 - Recomendación de talleres
    public List<Oportunidad> getTalleres() {
        return oportunidadRepository.findByTipoAndActivoTrue("TALLER");
    }

    // HU18 - Eventos
    public List<Oportunidad> getEventos() {
        return oportunidadRepository.findByTipoAndActivoTrue("EVENTO");
    }

    // Obtener todas las oportunidades activas
    public List<Oportunidad> getAllOportunidades() {
        return oportunidadRepository.findByActivoTrueOrderByFechaInicioDesc();
    }

    // Crear oportunidad (ADMIN)
    @Transactional
    public Oportunidad crearOportunidad(Oportunidad oportunidad) {
        oportunidad.setActivo(true);
        return oportunidadRepository.save(oportunidad);
    }

    // Actualizar oportunidad
    @Transactional
    public Oportunidad actualizarOportunidad(Long id, Oportunidad oportunidadActualizada) {
        Oportunidad oportunidad = oportunidadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Oportunidad no encontrada"));

        oportunidad.setTitulo(oportunidadActualizada.getTitulo());
        oportunidad.setDescripcion(oportunidadActualizada.getDescripcion());
        oportunidad.setEmpresa(oportunidadActualizada.getEmpresa());
        oportunidad.setUbicacion(oportunidadActualizada.getUbicacion());
        oportunidad.setRequisitos(oportunidadActualizada.getRequisitos());
        oportunidad.setFechaInicio(oportunidadActualizada.getFechaInicio());
        oportunidad.setFechaFin(oportunidadActualizada.getFechaFin());
        oportunidad.setLinkAplicacion(oportunidadActualizada.getLinkAplicacion());

        return oportunidadRepository.save(oportunidad);
    }

    // Desactivar oportunidad
    @Transactional
    public void desactivarOportunidad(Long id) {
        Oportunidad oportunidad = oportunidadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Oportunidad no encontrada"));
        oportunidad.setActivo(false);
        oportunidadRepository.save(oportunidad);
    }
}
