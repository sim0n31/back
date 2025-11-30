package com.upc.molinachirinostp.entity;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonBackReference;
=======
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
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
<<<<<<< HEAD
    @JsonBackReference
=======
>>>>>>> 4c40555585e49c001c9ff50bf066e75c03d1aaef
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private String tipo; // ME_GUSTA, ME_ENCANTA, ME_DIVIERTE, etc.
}
