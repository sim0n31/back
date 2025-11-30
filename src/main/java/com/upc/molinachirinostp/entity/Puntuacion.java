package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Puntuacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPuntuacion;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private Integer puntosPublicaciones = 0;
    private Integer puntosConexiones = 0;
    private Integer puntosMentorias = 0;
    private Integer puntosComentarios = 0;

    public Integer getTotalPuntos() {
        return puntosPublicaciones + puntosConexiones + puntosMentorias + puntosComentarios;
    }
}
