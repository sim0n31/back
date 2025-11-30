package com.upc.molinachirinostp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(unique = true, nullable = false)
    private String name; // ROLE_USER, ROLE_ADMIN, ROLE_MENTOR

    private String description;
}
