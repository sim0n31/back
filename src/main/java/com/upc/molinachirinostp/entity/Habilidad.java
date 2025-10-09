package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Habilidad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHabilidades;
    private String nombreHabilidad;
}
