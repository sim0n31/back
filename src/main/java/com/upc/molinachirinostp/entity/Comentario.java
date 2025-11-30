package com.upc.molinachirinostp.entity;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonBackReference;
=======
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
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
<<<<<<< HEAD
    @JsonBackReference
=======
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    @Column(nullable = false, length = 1000)
    private String contenido;

    private LocalDateTime fechaComentario;
}
