package com.upc.molinachirinostp.entity;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonManagedReference;
=======
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
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

<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.EAGER)
=======
    @ManyToOne
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    @Column(nullable = false, length = 5000)
    private String contenido;

    private String imagen;

    private LocalDateTime fechaPublicacion;

<<<<<<< HEAD
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
=======
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    private List<Reaccion> reacciones = new ArrayList<>();
}
