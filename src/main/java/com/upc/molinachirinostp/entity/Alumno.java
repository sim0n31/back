package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Alumno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlumno;

    private String semestre;
    private String estatusEstudio;

    @ManyToOne @JoinColumn(name="id_universidad")
    private Universidad universidad;

    @ManyToOne @JoinColumn(name="id_user")
    private Usuario usuario;   // FK a Usuario
}
