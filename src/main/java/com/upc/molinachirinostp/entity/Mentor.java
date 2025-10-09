package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Mentor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMentor;

    private String bio;

    @ManyToOne @JoinColumn(name="id_user")
    private Usuario usuario;   // opcional, recomendado
}
