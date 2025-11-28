package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    @Column(nullable = false, length = 5000)
    private String contenido;

    private String imagen;

    private LocalDateTime fechaPublicacion;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    private List<Reaccion> reacciones = new ArrayList<>();
}
