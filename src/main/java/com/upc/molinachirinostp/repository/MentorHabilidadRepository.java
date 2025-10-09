package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.MentorHabilidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorHabilidadRepository extends JpaRepository<MentorHabilidad, Long> {
    List<MentorHabilidad> findByMentorIdMentor(Long idMentor);
    List<MentorHabilidad> findByHabilidadIdHabilidades(Long idHabilidad);
}
