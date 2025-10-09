package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Universidad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUniversidad;

    private String nombre;
    private String pais;
    private String ciudad;
    private String siglas;

    @ManyToOne @JoinColumn(name="id_user")
    private Usuario creador;   // nullable
}
