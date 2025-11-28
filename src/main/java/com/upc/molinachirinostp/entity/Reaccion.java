package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReaccion;

    @ManyToOne
    @JoinColumn(name = "id_publicacion")
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private String tipo; // ME_GUSTA, ME_ENCANTA, ME_DIVIERTE, etc.
}
