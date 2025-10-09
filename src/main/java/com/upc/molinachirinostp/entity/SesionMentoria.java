package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class SesionMentoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSesion;

    private LocalDate fechaInicio; // en ERD es DATE

    private String canal;          // ej: ZOOM, PRESENCIAL
    private String estado;         // PENDIENTE, CONFIRMADA, etc.
    private String notas;

    @ManyToOne @JoinColumn(name="id_alumno")
    private Alumno alumno;

    @ManyToOne @JoinColumn(name="id_mentor")
    private Mentor mentor;
}
