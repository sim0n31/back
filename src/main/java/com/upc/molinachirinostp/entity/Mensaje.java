package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    @ManyToOne
    @JoinColumn(name = "id_emisor")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "id_receptor")
    private Usuario receptor;

    @Column(nullable = false, length = 2000)
    private String contenido;

    private LocalDateTime fechaEnvio;

    private Boolean leido = false;
}
