package com.upc.molinachirinostp.repository;

import com.upc.molinachirinostp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);

	// Búsqueda eficiente directamente en la base de datos
	@Query("SELECT u FROM Usuario u WHERE " +
		   "LOWER(u.primerNombre) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
		   "LOWER(u.primerApellido) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
		   "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))")
	List<Usuario> buscarPorNombreApellidoOEmail(@Param("query") String query);
}
