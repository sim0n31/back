package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String primerNombre;
    private String primerApellido;
    private String password;    // demo (en real: hash)
    private String pais;
    private String ciudad;
    private String email;
    private LocalDate fechaNacimiento;
    private String descripcion;
}
