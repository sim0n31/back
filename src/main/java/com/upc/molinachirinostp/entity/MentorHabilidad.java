package com.upc.molinachirinostp.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name="mentor_habilidades")
public class MentorHabilidad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="id_mentor")
    private Mentor mentor;

    @ManyToOne @JoinColumn(name="id_habilidades")
    private Habilidad habilidad;

    private LocalDate desde;
}
