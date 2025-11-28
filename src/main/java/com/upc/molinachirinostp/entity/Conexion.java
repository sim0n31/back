package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conexion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConexion;

    @ManyToOne
    @JoinColumn(name = "id_solicitante")
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "id_receptor")
    private Usuario receptor;

    @Column(nullable = false)
    private String estado; // PENDIENTE, ACEPTADA, RECHAZADA

    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;
}
