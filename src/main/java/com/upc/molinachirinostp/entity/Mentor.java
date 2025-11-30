package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Mentor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMentor;

    private String bio;

    private String areasExperiencia;

    private Integer anosExperiencia;

    private Double tarifaHora;

    private Boolean disponible = true;

    @ManyToOne @JoinColumn(name="id_user")
    private Usuario usuario;   // opcional, recomendado
}
