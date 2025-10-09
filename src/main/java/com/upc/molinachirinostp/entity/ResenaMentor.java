package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class ResenaMentor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @OneToOne @JoinColumn(name="id_sesion", unique=true)
    private SesionMentoria sesion;  // 1 reseña por sesión

    private Integer calificacion;   // 1..5
    private String comentario;
    private LocalDate fecha;
}
