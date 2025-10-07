package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
