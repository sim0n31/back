package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oportunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOportunidad;

    private String tipo; // EMPLEO, PASANTIA, TALLER, EVENTO

    @Column(nullable = false)
    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    private String empresa;
    private String ubicacion;
    private String requisitos;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String linkAplicacion;

    private Boolean activo = true;
}
