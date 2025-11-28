package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    @ManyToOne
    @JoinColumn(name = "id_publicacion")
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    @Column(nullable = false, length = 1000)
    private String contenido;

    private LocalDateTime fechaComentario;
}
